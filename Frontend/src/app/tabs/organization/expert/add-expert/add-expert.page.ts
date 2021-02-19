import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AlertController, NavController, ToastController } from '@ionic/angular';
import { Skill } from 'src/app/model/skill';
import { User } from 'src/app/model/user';
import { RestService } from 'src/app/services/rest.service';

@Component({
  selector: 'app-add-expert',
  templateUrl: './add-expert.page.html',
  styleUrls: ['./add-expert.page.scss'],
})
export class AddExpertPage {

  users:User[];
  organizationId: string;
  skill: Skill;

  constructor(
    private restService: RestService,
    private navCtrl:NavController,
    private route: ActivatedRoute,
    private alertController:AlertController,
    private toastController:ToastController
  ) {
    this.users = new Array();
    this.organizationId = this.route.snapshot.params["id"];
    this.loadUser();
   }

   async loadUser(event?){
    this.users = await this.restService.getUserPage(10);
    if(event){
      event.target.complete();
    }
  }

  async addExpert(user:User){
    const add = await this.alertController.create({
      cssClass: 'my-custom-class',
      header: 'Select Skill',
      message: '',
      inputs: this.createSkillInput(user),
      buttons: [
        {
          text: 'cancel',
        }, {
          text: 'add',
          handler: async data => {
            this.skill = new Skill();
            if (data==null) {
              const toast = await this.toastController.create({
                message: 'Campo Skill non selezionato',
                duration: 2000
              });
              toast.present();
            } else {
                this.skill = user.skills.find(obj=> obj==data);
                this.restService.addExpert(this.organizationId, user.mail, this.skill);
                this.goBack();
            }
          }
        }
      ]
    });
    await add.present();
  }

  createSkillInput(user:User) {
    const theNewInputs = [];
    var i:number = 1;
    user.skills .forEach(element => {
      theNewInputs.push(
        {
          type: 'radio',
          label: element.name+' '+element.level,
          value: element,
          checked: false
        }
      );
      i++;
    });
    return theNewInputs;
  }

  goBack() {
    this.navCtrl.navigateBack(["/list-of-organizations"], { queryParams: { 'refresh': 1 } });
  }

}
