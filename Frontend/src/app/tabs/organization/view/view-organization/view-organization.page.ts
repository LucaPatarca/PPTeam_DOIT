import { Organization } from './../../../../model/organization';
import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MenuController } from '@ionic/angular';
import { DataService } from 'src/app/services/data.service';
import { GlobalsService } from 'src/app/services/globals.service';

@Component({
  selector: 'app-view-organization',
  templateUrl: './view-organization.page.html',
  styleUrls: ['./view-organization.page.scss'],
})
export class ViewOrganizationPage {
  organization:Organization;

  constructor(
    private route:ActivatedRoute, 
    private router:Router,
    public data:DataService,
    private menuCtrl:MenuController, 
    private http:HttpClient,
    private globals:GlobalsService
    ) { 
    const id = this.route.snapshot.params["id"];
    this.organization = this.data.getOrganizationt(id);
    console.log(this.organization);
    this.menuCtrl.enable(false);
  }

  onBack(){
    this.menuCtrl.enable(true);
    this.router.navigate(["/list-of-organizations"], { queryParams: { 'refresh': 1 } });
  }

  deleteOrganization(){
    this.http.delete(this.globals.organizationApiUrl+this.organization.name).subscribe(
      res => {
        console.log("organization removed successfully");
        this.data.removeOrganizationt(this.organization);
      },
      err => {
        console.log("error on delete organization: "+err);
      },
    );
    this.onBack();
  }
}
