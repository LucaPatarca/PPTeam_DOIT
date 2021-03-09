import { Project } from 'src/app/model/project';
import { Organization } from 'src/app/model/organization';
import { RestService } from 'src/app/services/rest.service';
import { DataService } from 'src/app/services/data.service';
import { Platform, NavController } from '@ionic/angular';
import { Component } from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: 'home.page.html',
  styleUrls: ['home.page.scss'],
})
export class HomePage {

  HWBackSubscription: any;

  page:number;

  selection:string = "organizations";

  organizations:Organization[];
  projects:Project[];

  constructor(
    private platform: Platform,
    public dataService:DataService,
    private restService:RestService,
    private navCtrl:NavController
  ) {
    this.organizations = new Array();
    this.projects = new Array();
    if(this.dataService.isUserLogged())
      	this.loadOrganizations();        
  }

  ionViewDidEnter() {
    if(this.dataService.isUserLogged()){
      this.loadOrganizations();        
      this.loadProjects();
    }
    this.HWBackSubscription = this.platform.backButton.subscribe(() => {
      navigator['app'].exitApp();
    });
  }

  ionViewDidLeave() {
    this.HWBackSubscription.unsubscribe();
  }

  // metodo per richiedere una pagina di elementi
  loadOrganizations(event?) {
    if(this.dataService.isUserLogged())
      this.restService.getUserOrganizations(this.dataService.getUser().mail)
        .then(res => {        
          this.organizations = res;
          if (event)
            event.target.complete();
        }).catch(err => {
          this.organizations = new Array();
          if (event)
            event.target.complete();
        });
    else
      event.target.complete();
  }

  loadProjects(event?) {
    if(this.dataService.isUserLogged())
      this.restService.getUserProjects(this.dataService.getUser().mail)
        .then(res => {
          this.projects = res;
          if (event)
            event.target.complete();
        }).catch(err => {
          this.projects = new Array();
          if (event)
            event.target.complete();
      });
    else
      event.target.complete();
  }


  // metodo per aprire la visualizzazione di una pagina (gli si passa un organization)
  viewOrganization(organizationId: String) {
    this.navCtrl.navigateForward(['/tabs/list-of-organizations/view-organization', { "id": organizationId }]);
  }

  viewProject(projectId: String) {
    this.navCtrl.navigateForward(['/tabs/list-of-projects/view-project', { "id": projectId }]);
  }

  createOrganization() {
    this.dataService.modify = null;
    this.navCtrl.navigateForward(["/tabs/list-of-organizations/create-organization"]);
  }

  segmentChanged(event: any) {
    this.organizations = new Array();
    this.projects = new Array();
    this.selection = event.detail.value;
    if(this.selection=="organizations")
      this.loadOrganizations();
    else
      this.loadProjects();
  }

  isEmpty(): boolean {
    if (this.selection == "organizations") {
      return this.organizations.length == 0;
    } else {
      return this.projects.length == 0;
    }
  }

  getOrganizations():Organization[] {
    return this.organizations;
  }

  getProjects():Project[] {
    return this.projects;
  }

  isOrganizations():boolean{
    return this.selection=="organizations";
  }

}
