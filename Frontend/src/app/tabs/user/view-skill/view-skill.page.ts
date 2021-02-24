
import { Component } from '@angular/core';
import { MenuController, ActionSheetController, AlertController, ToastController } from '@ionic/angular';
import { Skill } from 'src/app/model/skill';
import { User } from 'src/app/model/user';
import { DataService } from 'src/app/services/data.service';
import { RestService } from 'src/app/services/rest.service';

@Component({
  selector: 'app-view-skill',
  templateUrl: './view-skill.page.html',
  styleUrls: ['./view-skill.page.scss'],
})
export class ViewSkillPage {

  skills: Skill[];
  page = 0;
  textNoSkill = "Nessuna skill disponibile";
  loading: boolean;

  constructor(
    public menuCtrl: MenuController,
    private restService: RestService,
    private dataService:DataService,
    private actionSheetCtrl:ActionSheetController,
    private alertController:AlertController,
    private toastController:ToastController,
  ) {
    this.loading = true;
    this.skills = new Array()
    this.loadSkills().then(
      ()=>{
        this.loading = false;
      }
    );
  }

  // metodo per richiedere una pagina di elementi
  async loadSkills(event?) {
    const newSkills = await  this.restService.getUserSkills((this.dataService.getUser() as unknown as User).mail);
    this.skills = newSkills;
    if(event){
      event.target.complete();
    }
  }

  async showActionSheet() {
    const actionSheet = await this.actionSheetCtrl.create({
      header: 'Skill',
      cssClass: 'my-custom-class',
      buttons: this.getButtons()
    });
    await actionSheet.present();
  }

  getButtons(): Array<Object> {
    var buttons = new Array();

    if (this.dataService.isUserLogged()) {
      buttons = buttons.concat([
         {
          text: 'Close',
          icon: 'close-outline',
          handler: () => {
          }
        }, {
          text: 'Add skill',
          icon: 'create-outline',
          handler: () => {
            this.addNewSkill();
          }
        }
      ]);
    }
    return buttons;
  }

  async addNewSkill(){
    const add = await this.alertController.create({
      cssClass: 'my-custom-class',
      header: 'Add skill name !',
      message: 'skill  name required',
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
              await this.restService.addNewSkill(data.skill);
              this.loadSkills();
            }
          }
        }
      ]
    });
    await add.present();
  }

  async deleteSkill(skill:Skill){
    const remove = await this.alertController.create({
      cssClass: 'my-custom-class',
      header: 'Remove skill !',
      message: `
      Skill info  
      <ul>
          <li>name : `+skill.name+`</li>
          <li>level : `+skill.level+`</li>
      </ul> 
      `,
      buttons: [
        {
          text: 'cancel',
        }, {
          text: 'remove',
          handler: async () => {
            await this.restService.removeSkill(skill);
            this.loadSkills();
          }
        }
      ]
    });
    await remove.present();
  }

}