import { Component, OnInit } from '@angular/core';
import { Organization } from 'src/app/model/organization';
import { Router, ActivatedRoute } from "@angular/router";
import { HttpClient,HttpHeaders } from '@angular/common/http';
import { MenuController, AlertController } from '@ionic/angular';
import { DataService } from 'src/app/services/data.service';
import { GlobalsService } from 'src/app/services/globals.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-modify-organization',
  templateUrl: './modify-organization.page.html',
  styleUrls: ['./modify-organization.page.scss'],
})
export class ModifyOrganizationPage implements OnInit {

  organization:Organization;
  validations_form: FormGroup;
  title:string;
  description:string;
  organization_already_exists:string = "";

  constructor(
    private route:ActivatedRoute, 
    public router:Router, 
    private menuCtrl:MenuController,
    public formBuilder:FormBuilder,
    private http:HttpClient,
    private dataService:DataService,
    private globals:GlobalsService,
    private alertCtrl:AlertController
  ) { 
    this.validations_form = this.formBuilder.group({
       
      title: ['', Validators.required],
      description: [Validators.required],
    });
    const id = this.route.snapshot.params['id'];
    this.organization = dataService.getOrganizationt(id);
    this.menuCtrl.enable(false);
  }

  ngOnInit(): void {
  }
    validation_messages = {
      'name': [
        { type: 'required', message: 'Name is required.' }
      ],
      'description': [
        { type: 'required', message: 'description is required.' }
      ],
    };

  save(){
    this.http.get(this.globals.existOrganizationApiUrl+this.organization.name).subscribe(
       res_1 =>{
        if(res_1==false){
          this.organization.name = this.title;
          this.organization.description = this.description;
          this.http.put(this.globals.modifyOrganizationApiUrl, this.organization, { headers: new HttpHeaders(), responseType: 'json'})
          .subscribe(
             async res => {
               console.log('Successfully saved Organization with Id: ' + this.organization.id);	
               this.dataService.updateOrganization(this.organization.id,res as Organization);
               const alert = await this.alertCtrl.create({
                 cssClass: 'my-custom-class',
                 header: 'Modificata',
                message: 'Oranizazione Modificata.',
                buttons: ['OK']
              });
              
              await alert.present();
              this.viewOrganization(res['id']);
             }, 
             err => { 
              console.log('There was an error!', err); 
             }
           );
         }else{
          console.log("organization already exist");
          this.organization_already_exists = this.title+" : organization already exist";
        }
      },
      err_1 =>{
          console.log(err_1);
      });
   }
  
  viewOrganization(id:string){
    this.router.navigate(['/view-organization',{'id':id}]);
  }

}
