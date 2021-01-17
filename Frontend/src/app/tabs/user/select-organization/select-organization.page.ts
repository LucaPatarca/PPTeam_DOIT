import { Organization } from 'src/app/model/organization';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { MenuController, NavController, AlertController } from '@ionic/angular';
import { DataService } from 'src/app/services/data.service';
import { GlobalsService } from 'src/app/services/globals.service';

@Component({
  selector: 'app-select-organization',
  templateUrl: './select-organization.page.html',
  styleUrls: ['./select-organization.page.scss'],
})
export class SelectOrganizationPage  {
  listOrganization:Organization[] = new Array();
  page = 0;
  textNoOrganizations="Nessuna Organizzazione disponibile";  

  constructor(
    private titleService: Title,
    private http: HttpClient,
    public data: DataService,
    public menuCtrl:MenuController,
    private navCtrl:NavController,
    private globals:GlobalsService,
    private alertCtrl:AlertController
    ) {
    this.data.clearOrganizationCreator();
    this.loadOrganizations();
    this.titleService.setTitle("selectOrg");
  }

  // metodo per richiedere una pagina di elementi
  loadOrganizations(event?){
    this.http.get(this.globals.getOrganizationUserCreatorApiUrl+this.data.userMail)
    .subscribe(res => {
      this.data.addOrganizationCreator(res as Organization);
      if(event){
        event.target.complete();
      }
    }, 
    err => { 
      console.log('oops some error in select org'); 
    });
  }

  loadMore(event: any){
        this.page++;
        this.loadOrganizations(event);
  }


  // metodo per aprire la visualizzazione di una pagina (gli si passa un organization)
  async selectOrg(organization:Organization){
    this.data.setOrgUser(organization.id);
    const alert = await this.alertCtrl.create({
      cssClass: 'my-custom-class',
      header: 'Selezionata',
      message: 'Organizzazione Selezionata.',
      buttons: ['OK']
    });
  
    await alert.present();
    this.onBack();
  }

  onBack(){
    this.menuCtrl.enable(true);
    this.navCtrl.navigateBack(["/home"], { queryParams: { 'refresh': 1 } })
  }

}
