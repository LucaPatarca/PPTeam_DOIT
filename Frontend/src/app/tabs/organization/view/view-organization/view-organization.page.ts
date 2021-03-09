import { Project } from './../../../../model/project';
import { Organization } from './../../../../model/organization';
import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NavController, ActionSheetController, AlertController, ToastController, ModalController } from '@ionic/angular';
import { DataService } from 'src/app/services/data.service';
import { RestService } from 'src/app/services/rest.service';
import { User } from 'src/app/model/user';
import { Skill } from 'src/app/model/skill';
import { SelectUserComponent } from 'src/app/components/select-user/select-user.component';

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
  loadingMembers: boolean;
  loadingProjects: boolean;
  members: User[];
  projects:Project[];
  creator: User;
  errorLoading: boolean;

  constructor(
    private route: ActivatedRoute,
    private nav: NavController,
    public dataService: DataService,
    private restService: RestService,
    private actionSheetCtrl: ActionSheetController,
    private alertController: AlertController,
    private toastController: ToastController,
    private modalCtrl: ModalController,
    private navCtrl:NavController
  ) {
    this.id = this.route.snapshot.params["id"];
    this.organization = null;
    this.creator = null;
    this.loadingMembers = true;
    this.loadingProjects = true;
    this.projects = new Array();
    this.members = new Array();
    this.errorLoading = false;
  }

  ionViewDidEnter() {
    this.loadOrganization()
      .then(v => {
        this.errorLoading = false;
        this.loadingMembers = false;
        this.loadingProjects = false;
      })
      .catch(err=>{
        this.loadingMembers = false;
        this.errorLoading = true;
        this.loadingProjects = true;
      });
  }

  async loadOrganization() {
    this.organization = await this.restService.getOrganization(this.id);
    const newMembers = await this.restService.getOrganizationMembers(this.id);
    this.creator = await this.restService.getUser(this.organization.creatorMail);
    //carica i membri escludendo il creatore
    var i = 0;
    newMembers.forEach(() => {
      const member = newMembers[i];
      if (member.mail == this.creator.mail) {
        newMembers.splice(i, 1);
      }
      i++;
    });
    this.members = newMembers;
    await this.loadProjects();
  }

  async loadProjects() {
    this.projects = await this.restService.getProjectsByOrganization(this.id);
  }

  async reload(event?) {
    this.loadOrganization()
      .then(res=>{
        this.errorLoading = false;
      }).catch(err=>{
        this.errorLoading = true;
      }).finally(()=>{
        event.target.complete();
      });
  }

  getExpertSkill(user: User): Skill[] {
    return user.skills.filter(it => it.expertInOrganization.includes(this.id) || it.level == 10);
  }

  async removeMember(user: User, slidingItem: any) {
    const alert = await this.alertController.create({
      message: "Remove " + user.name + " from this organization?",
      buttons: [
        "Cancel",
        {
          text: "Yes",
          handler: () => {
            //rimuove subito il member ma se c'è un errore sulla chiamata lo riaggiunge
            const index = this.members.indexOf(user);
            this.restService.removeMember(this.id, user.mail, false)
              .then(res => {
                if (!res)
                  this.members.splice(index, 0, user);
              }).catch(err => {
                this.members.splice(index, 0, user);
              });
            this.members.splice(index, 1);
          }
        }
      ]
    });
    alert.present();
    slidingItem.close();
  }

  async makeCollaborator(user: User, slidingItem: any) {
    const add = await this.alertController.create({
      header: 'Choose a Skill',
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
            if (data.skill == null || (data.skill as string).trim() == "") {
              const toast = await this.toastController.create({
                message: 'Campo skill non deve essere vuoto',
                duration: 2000
              });
              toast.present();
            } else {
              this.skill.name = data.skill;
              this.skill.expertInOrganization.push(this.id);
              this.skill.level = 1;
              this.restService.addCollaborator(this.id, user.mail, this.skill)
                .then(_val => this.members[this.members.indexOf(user)].skills.push(this.skill))
                .catch(err=>this.restService.presentToast("impossibile comunicare con il server"));
            }
          }
        }
      ]
    });
    add.present();
    slidingItem.close();
  }

  async showActionSheet() {
    const actionSheet = await this.actionSheetCtrl.create({
      header: 'Organization',
      cssClass: 'my-custom-class',
      buttons: this.getButtons()
    });
    await actionSheet.present();
  }

  async delete() {
    const alert = await this.alertController.create({
      message: "Delete organization?",
      buttons: [
        "Cancel",
        {
          text: "Yes",
          handler: () => {
            this.restService.deleteOrganization(this.organization).then(
              ()=>this.nav.navigateBack(["/tabs/list-of-organizations"], { queryParams: { 'refresh': 1 } })
            );
          }
        }
      ]
    });
    alert.present();
  }

  getButtons(): Array<Object> {
    var buttons = new Array();

    // azioni per i membri
    if (this.dataService.hasMemberPermission(this.organization)) {
      buttons = buttons.concat([
        {
          text: 'Edit',
          icon: 'create-outline',
          handler: () => {
            this.dataService.modify = this.organization;
            this.nav.navigateForward(['/tabs/list-of-organizations/view-organization/edit-organization']);
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
            this.delete();
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

  async addMember() {
    const modal = await this.modalCtrl.create({
      component: SelectUserComponent
    });
    modal.onWillDismiss().then(data => {
      if (data.data.user) {
        this.restService.addMember(this.id, data.data.user.mail).then(res => {
          if (res == true)
            this.members.push(data.data.user);
          else
            this.restService.presentToast("Impossibile aggiungere l'utente")
        });
      }
    });
    return await modal.present();
  }

  viewProject(id: string) {
    this.navCtrl.navigateForward(['/tabs/list-of-projects/view-project', { "id": id }]);
  }
}


