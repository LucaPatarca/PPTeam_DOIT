import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { Router } from '@angular/router';
import { MenuController, NavController } from '@ionic/angular';
import { DataService } from 'src/app/services/data.service';
import { GlobalsService } from 'src/app/services/globals.service';

@Component({
  selector: 'app-list-of-organizations',
  templateUrl: './list-of-organizations.page.html',
  styleUrls: ['./list-of-organizations.page.scss'],
})
export class ListOfOrganizationsPage  {
  page = 0;
  textNoOrganizations="Nessuna Organizzazione disponibile";  

  constructor(
    private titleService: Title,
    private http: HttpClient,
    public router:Router,
    public data: DataService,
    public menuCtrl:MenuController,
    private navCtrl:NavController,
    private globals:GlobalsService
    ) {
    this.data.clearOrganization();
    this.loadOrganizations();
    this.titleService.setTitle("listOfOrganizations");
    this.menuCtrl.enable(true);
  }

  // metodo per richiedere una pagina di elementi
  loadOrganizations(event?){
    this.http.get(this.globals.listOfOrganizationsApiUrl+this.page)
    .subscribe(res => {
      this.data.addOrganization(res['content']);
      if(event){
        event.target.complete();
      }
    });
  }

  loadMore(event: any){
        this.page++;
        this.loadOrganizations(event);
  }


  // metodo per aprire la visualizzazione di una pagina (gli si passa un organization)
  viewOrganization(organization:String){
    this.navCtrl.navigateForward(['/view-organization',{"id":organization}], { queryParams: { 'refresh': 1 } });
  }
}
