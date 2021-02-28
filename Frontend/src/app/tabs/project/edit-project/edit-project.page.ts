import { Skill } from '../../../model/skill';
import { Organization } from '../../../model/organization';
import { Component, OnInit } from '@angular/core';
import { MenuController, NavController, AlertController, ToastController } from '@ionic/angular';
import { DataService } from 'src/app/services/data.service';
import { Validators, FormBuilder, FormGroup } from '@angular/forms';
import { Project } from 'src/app/model/project';
import { RestService } from 'src/app/services/rest.service';
import { User } from 'src/app/model/user';
import { ActivatedRoute } from '@angular/router';
import { proxyOutputs } from '@ionic/angular/directives/proxies-utils';



@Component({
  selector: 'app-edit-project',
  templateUrl: './edit-project.page.html',
  styleUrls: ['./edit-project.page.scss'],
})
export class EditProjectPage implements OnInit{
  validations_form: FormGroup;
  project: Project;
  organizationName: string;
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
    private toastController:ToastController,
    private route: ActivatedRoute
    ) {
    this.validations_form = this.formBuilder.group({
      title: ['', Validators.required],
      description: ['', Validators.required],
    });
  }

  ngOnInit(): void {
    const id = this.route.snapshot.params['id'];
    this.project = new Project("","","",this.dataService.getUser().mail,new Array());
    this.organizationName = "";
    if(id){
      this.restService.getProject(id)
        .then(p=> {
          this.project = p;
          this.restService.getOrganization(this.project.organizationId).then(org=>{
            this.organizationName = org.name;
          });
        });
    } else{
      this.project.organizationId = this.dataService.getOrganization().id;
      this.organizationName = this.dataService.getOrganizationName();
    }
  }

  ionViewDidEnter() {
    this.menuCtrl.enable(false);
  }

  async addSkill(){
    const add = await this.alertController.create({
      cssClass: 'my-custom-class',
      header: 'Add Component !',
      message: 'skill required',
      inputs: [ 
        {
          name: 'skill',
          placeholder: 'skill'
        },
      ],
      buttons: [
        {
          text: 'cancel',
        }, {
          text: 'add',
          handler: async data => {
            if (data.skill==null || (data.skill as string).trim()=="") {
              const toast = await this.toastController.create({
                message: 'Campo skill non deve essere vuoto',
                duration: 2000
              });
              toast.present();
            } else {
              await this.getLevelSkill(data.skill);
            }
          }
        }
      ]
    });
    await add.present();
  }

  async getLevelSkill(skillName:string){
    const add = await this.alertController.create({
      cssClass: 'my-custom-class',
      header: 'skill level !',
      message: 'select level',
      inputs: [ 
        {
          type: 'radio',
          label: '1',
          value: '1'
        },
        {
          type: 'radio',
          label: '2',
          value: '2'
        },
        {
          type: 'radio',
          label: '3',
          value: '3'
        },
        {
          type: 'radio',
          label: '4',
          value: '4'
        },
        {
          type: 'radio',
          label: '5',
          value: '5'
        },
        {
          type: 'radio',
          label: '6',
          value: '6'
        },
        {
          type: 'radio',
          label: '7',
          value: '7'
        },
        {
          type: 'radio',
          label: '8',
          value: '8'
        },
        {
          type: 'radio',
          label: '9',
          value: '9'
        },
        {
          type: 'radio',
          label: '10',
          value: '10'
        }
      ],  
      buttons: [
        {
          text: 'add',
          handler: async data => {
            var skill = new Skill();
            skill.name = skillName;
            skill.level = data as number;
            this.project.neededSkills.push(skill);
          }
        }
      ]
    });
    await add.present();
  }

  createProject() {
    this.restService.createProject(this.project).then(
      value=>{
        this.goToProjectsList();
      }
    );
  }

  goToProjectsList() {
    this.navCtrl.navigateRoot(['/list-of-projects'], { queryParams: { 'refresh': 1 } });
  }
}
