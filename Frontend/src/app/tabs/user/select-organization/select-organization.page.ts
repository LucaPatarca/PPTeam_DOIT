import { Organization } from 'src/app/model/organization';
import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { MenuController, NavController, AlertController, ToastController } from '@ionic/angular';
import { DataService } from 'src/app/services/data.service';
import { GlobalsService } from 'src/app/services/globals.service';
import { RestService } from 'src/app/services/rest.service';

@Component({
  selector: 'app-select-organization',
  templateUrl: './select-organization.page.html',
  styleUrls: ['./select-organization.page.scss'],
})
export class SelectOrganizationPage {
  organizations: Organization[] = new Array();
  page = 0;
  textNoOrganizations = "Nessuna Organizzazione disponibile";
  loading: boolean;

  constructor(
    public data: DataService,
    public menuCtrl: MenuController,
    private navCtrl: NavController,
    private restService: RestService,
  ) {
    this.loading = true;
    this.organizations = new Array();
    this.loadOrganizations().then(
      ()=>{
        this.loading = false;
      }
    );
  }

  ionViewDidEnter() {
    this.menuCtrl.enable(true);
  }

  // metodo per richiedere una pagina di elementi
  async loadOrganizations() {
    const newOrganizations = await this.restService.getUserOrganizations(this.data.user.mail);
    this.organizations = this.organizations.concat(newOrganizations);
  }

  async loadMore(event: any) {
    this.page++;
    this.loadOrganizations().then(
      ()=>{
        if(event){
          event.target.complete();
        }
      }
    );
  }

  async reload(event?){
    this.page = 0;
    const newOrganizations = await this.restService.getUserOrganizations(this.data.user.mail);
    this.organizations = newOrganizations;
    event.target.complete();
  }

  // metodo per aprire la visualizzazione di una pagina (gli si passa un organization)
  async selectOrg(organization: Organization) {
    this.data.selectOrganization(organization);
    this.restService.presentToast("Organizzazione "+ organization.name + " selezionata");

    this.navCtrl.navigateBack(["/home"], { queryParams: { 'refresh': 1 } })
  }

}
