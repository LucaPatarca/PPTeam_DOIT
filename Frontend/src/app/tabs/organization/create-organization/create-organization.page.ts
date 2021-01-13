import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MenuController, NavController, AlertController } from '@ionic/angular';
import { Organization } from 'src/app/model/organization';
import { DataService } from 'src/app/services/data.service';
import { GlobalsService } from 'src/app/services/globals.service';

@Component({
  selector: 'app-create-organization',
  templateUrl: './create-organization.page.html',
  styleUrls: ['./create-organization.page.scss'],
})
export class CreateOrganizationPage {

  validations_form: FormGroup;
  name:string;
  description:string;
  organization_already_exists:string = "";
  
  constructor(private menuCtrl:MenuController, 
    public formBuilder:FormBuilder,
    private http: HttpClient,
    private dataService:DataService,
    private navCtrl:NavController,
    private globals:GlobalsService,
    private alertCtrl:AlertController
    ) { 
      this.menuCtrl.enable(false);
      this.validations_form = this.formBuilder.group({ 
        name: ['', Validators.required],
        description: ['', Validators.required],
      });
  }

  validation_messages = {
    'name': [
      { type: 'required', message: 'name is required.' },
    ],
    'email': [
      { type: 'required', message: 'Email is required.' },
      { type: 'pattern', message: 'Please enter a valid email.' }
    ]
  };

  createOrganization(){
    // metodo per effettuare una chiamata post
    const newOrganization = {
      "name": this.name,
      "description": this.description,
      "expertsMails": [],
      "membersMails": [],
      "creatorMail": this.dataService.getUser(),
      "collaboratorsMails": {},
    }
    this.http.post(this.globals.existProjectApiUrl,newOrganization.name).subscribe(
      res_1 =>{
        if(res_1==false){
          this.http.post(this.globals.createOrganizationApiUrl,newOrganization,{ headers: new HttpHeaders(), responseType: 'json'}).subscribe(
            async res => {
              console.log('Successfully created new organization');	
              this.dataService.addOrganization(res as Organization);
              const alert = await this.alertCtrl.create({
                cssClass: 'my-custom-class',
                header: 'Creata',
                message: 'Organizzazione Creata.',
                buttons: ['OK']
              });
            
              await alert.present();
            }, 
            err => { 
              console.log('oops some error in Organization'); 
            }
          );
        }else{
          console.log("organization already exist");
          this.organization_already_exists = this.name+" : organization already exist";
        }
      },err_1=>{
        console.log(err_1);
      }
    );
    this.menuCtrl.enable(true);
    this.navCtrl.navigateRoot(["/list-of-organizations"]);
  }

  back(){
    this.menuCtrl.enable(true);
    this.navCtrl.navigateRoot(["/home"], { queryParams: { 'refresh': 1 } });
  }

}
