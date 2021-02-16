import { Component } from '@angular/core';
import { MenuController, NavController, Platform } from '@ionic/angular';
import { OrganizationInformation } from 'src/app/model/organization-information';
import { RestService } from 'src/app/services/rest.service';

@Component({
  selector: 'app-list-of-organizations',
  templateUrl: './list-of-organizations.page.html',
  styleUrls: ['./list-of-organizations.page.scss'],
})
export class ListOfOrganizationsPage {
  page = 0;
  textNoOrganizations = "Nessuna Organizzazione disponibile";
  organizations: Array<OrganizationInformation>;
  loading: boolean;
  HWBackSubscription: any;

  constructor(
    public menuCtrl: MenuController,
    private navCtrl: NavController,
    private restService: RestService,
    private platform: Platform,
  ) {
    this.organizations = new Array();
    this.loading = true;
    this.loadOrganizations().then(
      ()=>{
        this.loading = false;
      }
    );
  }

  ionViewDidEnter() {
    this.menuCtrl.enable(true);
    this.HWBackSubscription = this.platform.backButton.subscribe(() => {
      navigator['app'].exitApp();
    });
  }

  ionViewDidLeave(){
    this.HWBackSubscription.unsubscribe();
  }

  // metodo per richiedere una pagina di elementi
  async loadOrganizations(event?) {
    const newOrganizations = await this.restService.getOrganizationPage(this.page);
    this.organizations = this.organizations.concat(newOrganizations);
    if (event) {
      event.target.complete();
    }
  }

  async reloadOrganizations(event?){
    this.page = 0;
    const newOrganizations = await this.restService.getOrganizationPage(this.page);
    this.organizations = newOrganizations;
    event.target.complete();
    
  }

  loadMore(event: any) {
    this.page++;
    this.loadOrganizations(event);
  }


  // metodo per aprire la visualizzazione di una pagina (gli si passa un organization)
  viewOrganization(organizationId: String) {
    this.navCtrl.navigateForward(['/view-organization', { "id": organizationId }], { queryParams: { 'refresh': 1 } });
  }
}
