import { AlertController, MenuController, NavController } from '@ionic/angular';
import { GlobalsService } from 'src/app/services/globals.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { DataService } from 'src/app/services/data.service';
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { User } from 'src/app/model/user';
import { computeStackId } from '@ionic/angular/directives/navigation/stack-utils';

@Component({
  selector: 'app-add-skill',
  templateUrl: './add-skill.page.html',
  styleUrls: ['./add-skill.page.scss'],
})
export class AddSkillPage {
  
  user:String;
  validations_form: FormGroup;
  skill:string;
  skill_already_exists:string = "";

  constructor(
    private route:ActivatedRoute,
    private formBuilder:FormBuilder ,
    private dataService:DataService,
    private http:HttpClient,
    private globals:GlobalsService,
    private alertCtrl:AlertController,
    private menuCtrl:MenuController,
    private navCtrl:NavController
  ) {
    this.user =this.route.snapshot.params["userMail"];
    this.validations_form = this.formBuilder.group({ 
      skill: ['', Validators.required],
    });
   }

   validation_messages = {
    'skill': [
      { type: 'required', message: 'name is required.' },
    ]
  };

  addSkill(){
    const newSkill = {
      "name": this.skill,
      "expertInOrganization": [this.dataService.orgUser],
      "isGloballyExpert": false
    }
    this.http.get(this.globals.userExistSkill+newSkill.name+"/"+this.user).subscribe(
      res_1 =>{
        if(res_1==false){
          this.http.post(this.globals.userAddSkillCollaborator+this.user,newSkill,{ headers: new HttpHeaders(), responseType: 'json'}).subscribe(
            async res => {
              console.log('Successfully created new skill');	
              const alert = await this.alertCtrl.create({
                cssClass: 'my-custom-class',
                header: 'Agiunta',
                message: 'Skill Creata.',
                buttons: ['OK']
              });
            
              await alert.present();
            }, 
            err => { 
              console.log(err);
              console.log('oops some error in skill'); 
            }
          );
        }else{
          console.log("skill already exist");
          this.skill_already_exists = this.skill+" : skill already exist";
        }
      },err_1=>{
        console.log(err_1);
      }
    );
    this.menuCtrl.enable(true);
    this.navCtrl.navigateRoot(["/list-of-organizations"]);
  }
  
}
