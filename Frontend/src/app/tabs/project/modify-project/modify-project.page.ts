import { Skill } from './../../../model/skill';
import { Component } from '@angular/core';
import { ActivatedRoute } from "@angular/router";
import { MenuController, NavController, AlertController, ToastController } from '@ionic/angular';
import { Project } from 'src/app/model/project';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { RestService } from 'src/app/services/rest.service';

@Component({
  selector: 'app-modify-project',
  templateUrl: './modify-project.page.html',
  styleUrls: ['./modify-project.page.scss'],
})
export class ModifyProjectPage {
  project: Project;
  validations_form: FormGroup;
  title: string;
  description: string;
  validation_messages = {
    'title': [
      { type: 'required', message: 'Name is required.' }
    ],
    'description': [
      { type: 'required', message: 'Email is required.' },
      { type: 'pattern', message: 'Please enter a valid email.' }
    ],
  };

  constructor(
    private alertController:AlertController,
    private route: ActivatedRoute,
    public nav: NavController,
    private menuCtrl: MenuController,
    public formBuilder: FormBuilder,
    private restService: RestService,
    private toastController:ToastController
  ) {
    this.validations_form = this.formBuilder.group({

      title: ['', Validators.required],
      description: [Validators.required],
    });
    const id = this.route.snapshot.params['id'];
    this.project = new Project("Title", "Description", "", "",new Array());
    restService.getProject(id).then(
      project=>{
        this.project = project;
      }
    )
  }

  ionViewDidlEnter() {
    this.menuCtrl.enable(false);
  }

  save() {
    this.project.title = this.title;
    this.project.description = this.description;
    this.restService.updateProject(this.project);
    this.goBack(this.project.id);
  }

  public goBack(id: string) {
    this.nav.navigateBack(['/view-project', { 'id': id }]);
  }

  async modifySkill(skill:Skill){
    var newSkill:Skill = new Skill();
    const add = await this.alertController.create({
      cssClass: 'my-custom-class',
      header: 'Modify skill name!',
      message: 'skill required',
      inputs: [ 
        {
          name: 'skill',
          placeholder: skill.name
        },
      ],
      buttons: [
        {
          text: 'cancel',
        }, {
          text: 'level',
          handler: async data => {
            if (data.skill==null || (data.skill as string).trim()=="") {
              const toast = await this.toastController.create({
                message: 'Campo skill non deve essere vuoto',
                duration: 2000
              });
              toast.present();
            } else {
              newSkill.name = data.skill;
              await this.modifySkillLevel(skill,newSkill);
            }
          }
        }
      ]
    });
    await add.present();
  }

  async modifySkillLevel(skill:Skill,newSkill:Skill){
    const add = await this.alertController.create({
      cssClass: 'my-custom-class',
      header: 'Modify skill level!',
      message: 'skill level required',
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
          text: 'cancel',
        }, {
          text: 'save',
          handler: async data => {
            if (data==null) {
              const toast = await this.toastController.create({
                message: 'Campo level skill non selezionato',
                duration: 2000
              });
              toast.present();
            } else {
              newSkill.level = data as number;
              this.project.neededSkills = this.project.neededSkills.filter(obj => obj !== skill);
              this.project.neededSkills.push(newSkill); 
            }
          }
        }
      ]
    });
    await add.present();
  }

}
