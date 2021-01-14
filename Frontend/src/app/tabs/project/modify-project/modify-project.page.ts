import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from "@angular/router";
import { HttpClient,HttpHeaders } from '@angular/common/http';
import { MenuController, AlertController, NavController } from '@ionic/angular';
import { Project } from 'src/app/model/project';
import { DataService } from 'src/app/services/data.service';
import { GlobalsService } from 'src/app/services/globals.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-modify-project',
  templateUrl: './modify-project.page.html',
  styleUrls: ['./modify-project.page.scss'],
})
export class ModifyProjectPage implements OnInit {
  project:Project;
  validations_form: FormGroup;
  title:string;
  description:string;
  project_already_exists:string = "";

  constructor(
    private route:ActivatedRoute, 
    public nav:NavController, 
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
    this.project = dataService.getProject(id);
    this.menuCtrl.enable(false);
  }

  ngOnInit(): void {
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
    

  save(){
    this.http.post(this.globals.existProjectApiUrl,this.project.organizationName.concat(".".concat(this.title))).subscribe(
      res_1 =>{
        if(res_1==false){
          this.project.name = this.title;
          this.project.description = this.description;
          this.http.put(this.globals.modifyProjectApiUrl, this.project, { headers: new HttpHeaders(), responseType: 'json'})
          .subscribe(
            async res => {
              console.log('Successfully saved Project with Id: ' + this.project.id);	
              this.dataService.updateProject(this.project.id,res as Project);
              const alert = await this.alertCtrl.create({
                cssClass: 'my-custom-class',
                header: 'Modificato',
                message: 'Progetto Modificato.',
                buttons: ['OK']
              });
            
              await alert.present();
              this.onBack(res['id']);
            }, 
            err => { 
              console.log('There was an error!', err); 
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

  public onBack(id:string){
    this.nav.navigateBack(['/view-project',{'id':id}]);
  }

}
