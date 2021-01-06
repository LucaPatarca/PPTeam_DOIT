import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { Router } from '@angular/router';
import { MenuController } from '@ionic/angular';
import { GlobalsService } from 'src/app/services/globals.service';

@Component({
  selector: 'app-list-of-organizations',
  templateUrl: './list-of-organizations.page.html',
  styleUrls: ['./list-of-organizations.page.scss'],
})
export class ListOfOrganizationsPage implements OnInit {

  organizations = [];
  page = 0;
  textNoOrganizations="";  

  constructor(
    private titleService: Title,
    private http: HttpClient,
    public router:Router,
    public menuCtrl:MenuController,
    private globals:GlobalsService
    ) {
    this.loadOrganizations();
    this.titleService.setTitle("listOfOrganizations");
    this.menuCtrl.enable(true);
  }
  ngOnInit(): void {
  }

  // metodo per richiedere una pagina di elementi
  loadOrganizations(event?){
    this.http.get(this.globals.listOfOrganizationsApiUrl+this.page)
    .subscribe(res => {
      this.organizations= this.organizations.concat(res['content']);
      if(this.organizations.length==0){
        this.textNoOrganizations = "Nessuna Organizzazione disponibile";
      }else{
        this.textNoOrganizations = "";
      }
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
    this.router.navigate(['/view-organization',organization]);
  }
}
