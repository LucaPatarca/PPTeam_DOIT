import { Organization } from './../../../../model/organization';
import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { MenuController, AlertController, NavController, ToastController } from '@ionic/angular';
import { DataService } from 'src/app/services/data.service';
import { GlobalsService } from 'src/app/services/globals.service';

@Component({
  selector: 'app-view-organization',
  templateUrl: './view-organization.page.html',
  styleUrls: ['./view-organization.page.scss'],
})
export class ViewOrganizationPage {
  organization: Organization;

  constructor(
    private route: ActivatedRoute,
    private nav: NavController,
    public data: DataService,
    private menuCtrl: MenuController,
    private http: HttpClient,
    private globals: GlobalsService,
    private toastCtrl: ToastController,
    private alertCtrl: AlertController,
  ) {
    const id = this.route.snapshot.params["id"];
    this.organization = this.data.getOrganization(id);
  }

  ionViewWillEnter() {
    this.menuCtrl.enable(false);
  }

  goBack() {
    this.nav.navigateBack(["/list-of-organizations"], { queryParams: { 'refresh': 1 } });
  }

  deleteOrganization() {
    this.http.delete(this.globals.organizationApiUrl + this.organization.id).subscribe(
      async res => {
        if (this.organization.creatorMail == this.data.user.mail) {
          this.data.quitFromOrganization();
        }
        console.log("organization removed successfully");
        this.data.removeOrganization(this.organization);

        const toast = await this.toastCtrl.create({
          message: res == true ? 'Organizzazione Cancellata.' : 'Organizzazione Non Cancellata',
          duration: 2000
        });
        toast.present();
      },
      async err => {
        const toast = await this.toastCtrl.create({
          message: err.error,
          duration: 2000
        });
        toast.present();
      },
    );
    this.goBack();
  }

  modifyOrganization() {
    this.nav.navigateForward(['/modify-organization', { "id": this.organization.id }]);
  }

  async addExpert() {
    const alert = await this.alertCtrl.create({
      cssClass: 'my-custom-class',
      header: 'Scegli',
      message: 'Che tipo di esperto?',
      buttons: [
        {
          text: 'Interno',
          handler: () => {
            this.nav.navigateForward(["/add-collaborator"]);
          }
        }, {
          text: 'Esterno',
          handler: () => {
            this.nav.navigateForward(["/add-expert"]);
          }
        }
      ]
    });

    alert.present();
  }

}


