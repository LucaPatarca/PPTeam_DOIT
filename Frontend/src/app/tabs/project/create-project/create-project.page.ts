import { HttpClient,HttpHeaders } from '@angular/common/http';
import { Component } from '@angular/core';
import { MenuController, NavController } from '@ionic/angular';
import { GlobalsService } from 'src/app/services/globals.service';
import { DataService } from 'src/app/services/data.service';
import { Validators, FormBuilder, FormGroup } from '@angular/forms';
import { Project } from 'src/app/model/project';



@Component({
  selector: 'app-create-project',
  templateUrl: './create-project.page.html',
  styleUrls: ['./create-project.page.scss'],
})
export class CreateProjectPage {

  validations_form: FormGroup;
  title:string;
  description:string;
  project_already_exists:string = "";

  constructor(private menuCtrl:MenuController,
    private http:HttpClient,
    public formBuilder:FormBuilder,
    private navCtrl:NavController,
    private dataService:DataService,
    private globals: GlobalsService) { 
    this.menuCtrl.enable(false);
    this.validations_form = this.formBuilder.group({ 
      title: ['', Validators.required],
      description: ['', Validators.required],
    });
  }
    validation_messages = {
      'name': [
        { type: 'required', message: 'Name is required.' }
      ],
      'email': [
        { type: 'required', message: 'Email is required.' },
        { type: 'pattern', message: 'Please enter a valid email.' }
      ],
    };

  createProject(){
    // metodo per effettuare una chiamata post
    var newProject = {
      "name":this.title,
      "description":this.description,
      "organizationName":this.globals.defaultOrganizationName,
      "creatorMail":this.globals.userMail,
      "neededSkills":[],
      "closed":false,
      "team":[],
      "candidates":[]
    };
    this.http.post(this.globals.existProjectApiUrl,newProject.organizationName.concat(".".concat(newProject.name))).subscribe(
      res_1 =>{
        if(res_1==false){
          this.http.post(this.globals.createProjectApiUrl,newProject,{ headers: new HttpHeaders(), responseType: 'json'}).subscribe(
            res => {
              console.log('Successfully created new project');
              this.menuCtrl.enable(true);
              this.dataService.addProject(res as Project);
              this.navCtrl.navigateRoot(['/list-of-projects']);
            }, 
            err => { 
              console.log('oops some error in Project'); 
            }
          );
        }else{
          console.log("project already exist");
          this.project_already_exists = this.title+" : project already exist";
        }
      },
      err_1 =>{
          console.log(err_1);
      });
  
  }

  back(){
    this.menuCtrl.enable(true);
    this.navCtrl.navigateRoot(['/home'], { queryParams: { 'refresh': 1 } });
  }
}
