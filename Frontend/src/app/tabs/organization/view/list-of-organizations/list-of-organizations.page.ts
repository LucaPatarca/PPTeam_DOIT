import { Organization } from './../../../../model/organization';
import { DataService } from 'src/app/services/data.service';
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
  allOrganizations: Array<OrganizationInformation>;
  yourOrganizations: Array<Organization>;
  selection: string;
  loading: boolean;
  HWBackSubscription: any;

  constructor(
    public menuCtrl: MenuController,
    private navCtrl: NavController,
    private restService: RestService,
    private platform: Platform,
    public dataService: DataService
  ) {
    this.allOrganizations = new Array();
    this.yourOrganizations = new Array();
    this.loading = true;
    this.selection = 'all';
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
    this.reloadOrganizations();
  }

  ionViewDidLeave(){
    this.HWBackSubscription.unsubscribe();
  }

  // metodo per richiedere una pagina di elementi
  async loadOrganizations(event?) {
    if(this.selection == "all"){
      const newOrganizations = await this.restService.getOrganizationPage(this.page);
      this.allOrganizations = this.allOrganizations.concat(newOrganizations);
    } else{
      const newOrganizations = await this.restService.getUserOrganizations(this.dataService.getUser().mail);
      this.yourOrganizations = this.yourOrganizations.concat(newOrganizations);
    }
    if (event) {
      event.target.complete();
    }
  }

  async reloadOrganizations(event?){
    if(this.selection == "all"){
      this.page = 0;
      const newOrganizations = await this.restService.getOrganizationPage(this.page);
      this.allOrganizations = newOrganizations;
    } else {
      const newOrganizations = await this.restService.getUserOrganizations(this.dataService.getUser().mail);
      this.yourOrganizations = newOrganizations;
    }
    if(event)
      event.target.complete();
    
  }

  loadMore(event: any) {
    if(this.selection == "all"){
      this.page++;
      this.loadOrganizations(event);
    }
  }


  // metodo per aprire la visualizzazione di una pagina (gli si passa un organization)
  viewOrganization(organizationId: String) {
    this.navCtrl.navigateForward(['/tabs/list-of-organizations/view-organization', { "id": organizationId }]);
  }

  createOrganization(){
    this.navCtrl.navigateForward(["/tabs/list-of-organizations/create-organization"]);
  }

  segmentChanged(event:any){
    this.selection = event.detail.value;
    if(this.isOrganizationsEmpty()){
      this.loadOrganizations();
    }
  }

  isOrganizationsEmpty(): boolean{
    if(this.selection == "all"){
      return this.allOrganizations.length==0;
    } else{
      return this.yourOrganizations.length==0;
    }
  }

  getOrganizations(){
    if(this.selection == "all"){
      return this.allOrganizations;
    } else{
      return this.yourOrganizations;
    }
  }
}
