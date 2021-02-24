import { element } from 'protractor';
import { Organization } from './../../../../model/organization';
import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { MenuController, NavController, ActionSheetController, AlertController, ToastController } from '@ionic/angular';
import { DataService } from 'src/app/services/data.service';
import { RestService } from 'src/app/services/rest.service';
import { User } from 'src/app/model/user';
import { Skill } from 'src/app/model/skill';

@Component({
  selector: 'app-view-organization',
  templateUrl: './view-organization.page.html',
  styleUrls: ['./view-organization.page.scss'],
})
export class ViewOrganizationPage {
  organization: Organization;
  id: string;
  userMail: string;
  skill: Skill;
  user: User;

  constructor(
    private route: ActivatedRoute,
    private nav: NavController,
    public dataService: DataService,
    private menuCtrl: MenuController,
    private restService: RestService,
    private actionSheetCtrl: ActionSheetController,
    private alertController:AlertController,
    private toastController:ToastController
  ) {
    this.id = this.route.snapshot.params["id"];
    this.organization = null;
  }

  ionViewDidEnter() {
    this.menuCtrl.enable(false);
    this.loadOrganization();
  }

  async loadOrganization() {
    this.organization = await this.restService.getOrganization(this.id);

  }

  async reload(event?) {
    await this.loadOrganization();
    event.target.complete();
  }

  goBack() {
    this.nav.navigateBack(["/list-of-organizations"], { queryParams: { 'refresh': 1 } });
  }

  async showActionSheet() {
    const actionSheet = await this.actionSheetCtrl.create({
      header: 'Organization',
      cssClass: 'my-custom-class',
      buttons: this.getButtons()
    });
    await actionSheet.present();
  }

  async howAddExpert() {
    const add = await this.alertController.create({
      cssClass: 'my-custom-class',
      header: 'How Add Expert',
      buttons: [
        {
          text: 'email',
          handler: () => { this.addExpertByEmail() }
        }, {
          text: 'list',
          handler: () => { this.nav.navigateForward(["/add-expert", { "id": this.organization.id }]); }
        }, {
          text: 'cancel',
        }
      ]
    });
    await add.present();
  }

  createSkillInput(user:User) {
    const theNewInputs = [];
    user.skills .forEach(element => {
    if(!element.expertInOrganization.includes(this.organization.id)){
        theNewInputs.push(
        {
          type: 'radio',
          label: element.name+' '+element.level,
          value: element,
          checked: false
        }
      );
    }
    });
    return theNewInputs;
  }

  async addExpertByEmail() {
    const add = await this.alertController.create({
      cssClass: 'my-custom-class',
      header: 'Add Expert by Email',
      inputs: [ 
        {
          name: 'email',
          placeholder: 'email'
        },
      ],
      buttons: [
        {
          text: 'ok',
          handler: async data => {
            if (data.email==null || (data.email as string).trim()=="") {
              const toast = await this.toastController.create({
                message: 'Campo email non deve essere vuoto',
                duration: 2000
              });
              toast.present();
            } if (await !this.restService.existUser(data.email as string) as boolean) {
              const toast = await this.toastController.create({
                message: 'Utente non presente',
                duration: 2000
              });
              toast.present();
            } else {
                this.user = await this.restService.getUser(data.email);
                if(this.user.skills.length == 0){
                  const toast = await this.toastController.create({
                    message: 'Utente non ha skill selezionabili',
                    duration: 2000
                  });
                  toast.present();
                } else this.selectSkill(this.user);
            }
          }
        }, {
          text: 'cancel',
        }
      ]
    });
    await add.present();
  }

  async selectSkill(user:User){
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
                this.restService.addExpert(this.id, this.userMail, this.skill);
                this.goBack();
            }
          }
        }
      ]
    });
    await add.present();
  }

  getButtons(): Array<Object> {
    var buttons = new Array();

    // azioni per i membri
    if (this.dataService.hasOrganizationCreatorPermission(this.organization)) {
      buttons = buttons.concat([
        {
          text: 'Edit',
          icon: 'create-outline',
          handler: () => {
            this.nav.navigateForward(['/modify-organization', { "id": this.organization.id }]);
          }
        }
      ]);
    }

    // azioni per il cratore
    if (this.dataService.hasOrganizationCreatorPermission(this.organization)) {
      buttons = buttons.concat([
        {
          text: 'Delete',
          role: 'destructive',
          icon: 'trash',
          handler: () => {
            this.restService.deleteOrganization(this.organization);
            this.goBack();
          }
        }, {
          text: 'Add Member',
          icon: 'person-add-outline',
          handler: () => {
            this.addMember();
          }
        }, {
          text: 'Add Expert',
          icon: 'person-add-outline',
          handler: () => {
            this.howAddExpert();
          }
        }, {
          text: 'Add Collaborator',
          icon: 'person-add-outline',
          handler: () => {
            this.nav.navigateForward(["/add-collaborator", { "id": this.organization.id }]);
          }
        },
        {
          text: 'Remove Member',
          icon: 'person-remove-outline',
          handler: () => {
             this.removeMember();
             this.nav.navigateForward(['/view-organization', { "id": this.id }], { queryParams: { 'refresh': 1 } });
            }
        }
      ]);
    }

    // azioni per tutti
    buttons = buttons.concat([
      {
        text: 'Cancel',
        icon: 'close',
        role: 'cancel',
        handler: () => { }
      }
    ]);

    return buttons;
  }

  async removeMember(){
    const removeMember = await this.alertController.create({
      cssClass: 'my-custom-class',
      header: 'Select Member',
      inputs: this.createMemberInput(),
      buttons: [
        {
          text: 'cancel',
        }, {
          text: 'remove',
          handler: async data => {
            if (data==null) {
              const toast = await this.toastController.create({
                message: 'Campo Member non selezionato',
                duration: 2000
              });
              toast.present();
            } else {
                this.restService.removeMember(data);
            }
          }
        }
      ]
    });
    await removeMember.present();
  }

  createMemberInput() {
    const theNewInputs = [];
    this.dataService.getOrganization().membersMails.forEach(async element => {
    if(!(element == this.dataService.getUserMail())){
        theNewInputs.push(
        {
          type: 'radio',
          label: element,
          value: element,
          checked: false
        }
      );
    }
    });
    return theNewInputs;
  }

  async addMember(){
    const add = await this.alertController.create({
      cssClass: 'my-custom-class',
      header: 'Enter Member mail',
      message: '',
      inputs: [
        {
          name: 'mail',
          type: 'text',
          placeholder: 'Member Mail'
        },
      ],
      buttons: [
        {
          text: 'cancel',
        }, {
          text: 'add',
          handler: async data => {
            if (data.mail==null||(data.mail as string).trim() == "") {
              const toast = await this.toastController.create({
                message: 'Campo mail non valido',
                duration: 2000
              });
              toast.present();
            } else {
                this.restService.addMember(data.mail as string);
                this.goBack();
            }
          }
        }
      ]
    });
    await add.present();
  }

}


