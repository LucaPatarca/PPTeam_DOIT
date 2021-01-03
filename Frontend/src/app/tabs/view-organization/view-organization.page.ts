import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { MenuController } from '@ionic/angular';

@Component({
  selector: 'app-view-organization',
  templateUrl: './view-organization.page.html',
  styleUrls: ['./view-organization.page.scss'],
})
export class ViewOrganizationPage implements OnInit {
  data:any

  constructor(
    private route:ActivatedRoute , private menuCtrl:MenuController , private http:HttpClient) { 
    this.data = this.route.snapshot.params;
    this.menuCtrl.enable(false);
  }

  ngOnInit() {
  }

  onBack(){
    this.menuCtrl.enable(true);
  }

  deleteOrganization(){
    this.http.delete("http://localhost:8080/api/organizations/"+this.data.name).subscribe(
      res => {
        console.log("organization removed successfully");
      },
      err => {
        console.log("error on delete organization: "+err);
      },
    );
    this.onBack();
  }
}
