import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { MenuController } from '@ionic/angular';
import { GlobalsService } from 'src/app/services/globals.service';

@Component({
  selector: 'app-create-organization',
  templateUrl: './create-organization.page.html',
  styleUrls: ['./create-organization.page.scss'],
})
export class CreateOrganizationPage implements OnInit {

  organization_description:string
  organization_name:string
  constructor(private menuCtrl:MenuController, 
    private http: HttpClient,
    private globals:GlobalsService
    ) { 
      this.menuCtrl.enable(false)
  }

  ngOnInit() {
    
  }

  createOrganization(){
    // metodo per effettuare una chiamata post
    const newOrganization = {
      "name": this.organization_name,
      "description": this.organization_description,
      "expertsMails": [],
      "membersMails": [
        this.globals.userMail
      ],
      "creatorMail": this.globals.userMail,
      "collaboratorsMails": {},
    }
    this.http.post(this.globals.createOrganizationApiUrl,newOrganization,{ headers: new HttpHeaders(), responseType: 'json'}).subscribe(
      res => {
        console.log('Successfully created new organization');	
      }, 
      err => { 
        console.log('oops some error in Organization'); 
      }
    );
    this.menuCtrl.enable(true);
  }

}
