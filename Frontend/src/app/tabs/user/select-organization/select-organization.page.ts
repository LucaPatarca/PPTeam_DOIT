import { Organization } from 'src/app/model/organization';
import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { MenuController, NavController, AlertController, ToastController } from '@ionic/angular';
import { DataService } from 'src/app/services/data.service';
import { GlobalsService } from 'src/app/services/globals.service';

@Component({
  selector: 'app-select-organization',
  templateUrl: './select-organization.page.html',
  styleUrls: ['./select-organization.page.scss'],
})
export class SelectOrganizationPage {
  listOrganization: Organization[] = new Array();
  page = 0;
  textNoOrganizations = "Nessuna Organizzazione disponibile";

  constructor(
    private http: HttpClient,
    public data: DataService,
    public menuCtrl: MenuController,
    private navCtrl: NavController,
    private globals: GlobalsService,
    private toastCtrl: ToastController,
  ) {
    this.loadOrganizations();
  }

  ionViewWillEnter() {
    this.menuCtrl.enable(true);
  }

  // metodo per richiedere una pagina di elementi
  loadOrganizations(event?) {
    this.http.get(this.globals.getOrganizationUserCreatorApiUrl + this.data.getUser().mail)
      .subscribe(
        res => {
          this.data.addUserOrganization(res as Organization);
          if (event) {
            event.target.complete();
          }
        },
        err => {
          console.log('oops some error in select org');
        }
      );
  }

  loadMore(event: any) {
    this.page++;
    this.loadOrganizations(event);
  }


  // metodo per aprire la visualizzazione di una pagina (gli si passa un organization)
  async selectOrg(organization: Organization) {
    this.data.selectOrganization(organization);
    const toast = await this.toastCtrl.create({
      message: 'Organizzazione Selezionata.',
      duration: 2000
    });
    toast.present();

    this.goBack();
  }

  goBack() {
    this.navCtrl.navigateBack(["/home"], { queryParams: { 'refresh': 1 } })
  }

}
