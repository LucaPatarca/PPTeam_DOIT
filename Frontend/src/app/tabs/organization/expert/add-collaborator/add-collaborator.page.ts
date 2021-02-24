import { DataService } from './../../../../services/data.service';
import { AlertController, NavController, ToastController } from '@ionic/angular';
import { Component } from '@angular/core';
import { User } from 'src/app/model/user';
import { RestService } from 'src/app/services/rest.service';
import { ActivatedRoute } from '@angular/router';
import { Skill } from 'src/app/model/skill';
import { Organization } from 'src/app/model/organization';

@Component({
  selector: 'app-add-collaborator',
  templateUrl: './add-collaborator.page.html',
  styleUrls: ['./add-collaborator.page.scss'],
})
export class AddCollaboratorPage {

  users:User[];
  organizationId: string;
  skill:Skill;

  constructor(
    private restService: RestService,
    private navCtrl:NavController,
    private route: ActivatedRoute,
    private alertController:AlertController,
    private toastController:ToastController,
    private dataService:DataService
  ) { 
    this.users = new Array();
    this.organizationId = this.route.snapshot.params["id"];
    this.loadUser();
  }

  async loadUser(event?){
    this.users = await this.restService.getOrganizationMembers(this.organizationId);
    if(event){
      event.target.complete();
    }
  }

  async addSkill(user:User){
    const add = await this.alertController.create({
      cssClass: 'my-custom-class',
      header: 'Add a Skill',
      message: '',
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
            this.skill = new Skill();
            if (data.skill==null || (data.skill as string).trim()=="") {
              const toast = await this.toastController.create({
                message: 'Campo skill non deve essere vuoto',
                duration: 2000
              });
              toast.present();
            } else {
                this.skill.name = data.skill;
                this.skill.expertInOrganization.push((await this.dataService.getOrganization() as unknown as Organization).id as string)
                this.skill.level = 0;
                console.log(this.skill);
                this.restService.addCollaborator(this.organizationId, user.mail, this.skill)
                this.goBack();
            }
          }
        }
      ]
    });
    await add.present();
  }

  goBack() {
    this.navCtrl.navigateBack(["/list-of-organizations"], { queryParams: { 'refresh': 1 } });
  }
}
