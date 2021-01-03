import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { MenuController } from '@ionic/angular';

@Component({
  selector: 'app-create-organization',
  templateUrl: './create-organization.page.html',
  styleUrls: ['./create-organization.page.scss'],
})
export class CreateOrganizationPage implements OnInit {

  organization_description:string
  organization_name:string
  constructor(private menuCtrl:MenuController, private http: HttpClient) { 
    this.menuCtrl.enable(false)
  }

  ngOnInit() {
  }

  createOrganization(){
    // metodo per effettuare una chiamata post
    const newOrganization = {
      "name": this.organization_name,
      "description": this.organization_description,
      "expertsMails": {},
      "membersMails": [
        "mail"
      ],
      "creatorMail": "mail",
    }
    this.http.post("http://localhost:8080/api/organizations/createNew",newOrganization,{ headers: new HttpHeaders(), responseType: 'json'}).subscribe(
      res => {
        console.log('res', res);	
      }, 
      err => { 
        console.log('oops some error in Project'); 
      }
    );
    this.menuCtrl.enable(true);
  }

}
