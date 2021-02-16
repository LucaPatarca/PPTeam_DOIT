import { Skill } from './../../../model/skill';
import { Organization } from './../../../model/organization';
import { Component } from '@angular/core';
import { MenuController, NavController, AlertController, ToastController } from '@ionic/angular';
import { DataService } from 'src/app/services/data.service';
import { Validators, FormBuilder, FormGroup } from '@angular/forms';
import { Project } from 'src/app/model/project';
import { RestService } from 'src/app/services/rest.service';
import { User } from 'src/app/model/user';



@Component({
  selector: 'app-create-project',
  templateUrl: './create-project.page.html',
  styleUrls: ['./create-project.page.scss'],
})
export class CreateProjectPage {
  [x: string]: any;

  validations_form: FormGroup;
  title: string;
  description: string;
  neededSkills:Skill[] = new Array();
  skill:Skill = new Skill();
  validation_messages = {
    'title': [
      { type: 'required', message: 'Name is required.' }
    ],
    'description': [
      { type: 'required', message: 'Email is required.' },
      { type: 'pattern', message: 'Please enter a valid email.' }
    ],
  };

  constructor(private menuCtrl: MenuController,
    public formBuilder: FormBuilder,
    private navCtrl: NavController,
    private alertController:AlertController,
    public dataService: DataService,
    private restService: RestService,
    private toastController:ToastController
    ) {
    this.validations_form = this.formBuilder.group({
      title: ['', Validators.required],
      description: ['', Validators.required],
    });
  }

  ionViewDidEnter() {
    this.menuCtrl.enable(false);
  }

  async addComponent(){
    const add = await this.alertController.create({
      cssClass: 'my-custom-class',
      header: 'Add Component !',
      message: 'skill required',
      inputs: [ 
        {
          name: 'skill',
          placeholder: 'skill'
        },
        {
          name: 'level',
          placeholder: 'level'
        },
      ],
      buttons: [
        {
          text: 'cancel',
        }, {
          text: 'add',
          handler: async data => {
            this.skill = new Skill();
            if (data.skill==null || (data.skill as string).trim()=="") {
              const toast = await this.toastController.create({
                message: 'Campo skill non deve essere vuoto',
                duration: 2000
              });
              toast.present();
            } else {
              try{
                this.skill.name = data.skill;
                this.skill.level = data.level as number;
                if(this.skill.level>10||this.skill.level<1)
                  throw new Error("");
                this.skill.name = data.skill;
                this.skill.level = data.level as number;
                this.neededSkills.push(this.skill);
              }catch{
                const toast = await this.toastController.create({
                  message: 'Campo level deve essere compreso tra 1 e 10',
                  duration: 2000
                });
                toast.present();
              }
            }
          }
        }
      ]
    });
    await add.present();
  }

  createProject() {
    // metodo per effettuare una chiamata post
    const project = new Project(
      this.title,
      this.description,
      (this.dataService.getOrganization() as unknown as Organization).id,
      (this.dataService.getUser() as unknown as User).mail,
      this.neededSkills,
    );
    
    this.restService.createProject(project).then(
      value=>{
        this.goToProjectsList();
      }
    );
  }

  goToProjectsList() {
    this.navCtrl.navigateRoot(['/list-of-projects'], { queryParams: { 'refresh': 1 } });
  }
}
