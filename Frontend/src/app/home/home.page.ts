import { YourOrganizationsComponent } from './../components/your-organizations/your-organizations.component';
import { YourProjectsComponent } from './../components/your-projects/your-projects.component';
import { Project } from 'src/app/model/project';
import { Organization } from 'src/app/model/organization';
import { RestService } from 'src/app/services/rest.service';
import { DataService } from 'src/app/services/data.service';
import { Platform, NavController, ModalController } from '@ionic/angular';
import { Component } from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: 'home.page.html',
  styleUrls: ['home.page.scss'],
})
export class HomePage {

  HWBackSubscription: any;

  page:number;

  isVisibleProjects=false;
  isVisibleOrgnizations=false;

  selection:string = "organizations";

  organizations:Organization[];
  projects:Project[];

  constructor(
    private platform: Platform,
    public dataService:DataService,
    private restService:RestService,
    private navCtrl:NavController,
    private modalCtrl: ModalController
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

  getLengthOrganizations():number{
    return this.organizations.length;
  }

  getLengthProjects():number{
    return this.projects.length;
  }

  async openProjects(){
    const modal = await this.modalCtrl.create({
      component: YourProjectsComponent,
      componentProps:{
        projects: this.projects
      }
    });
    modal.present();
  }

  async openOrganizations(){
    const modal = await this.modalCtrl.create({
      component: YourOrganizationsComponent,
      componentProps:{
        organizations: this.organizations
      }
    });
    modal.present();
  }

}
