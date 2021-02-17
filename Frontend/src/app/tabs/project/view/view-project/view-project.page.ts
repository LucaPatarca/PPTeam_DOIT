import { Organization } from './../../../../model/organization';
import { ActivatedRoute } from '@angular/router';
import { Component } from '@angular/core';
import { ActionSheetController, AlertController, MenuController, NavController, ToastController } from '@ionic/angular';
import { Project } from 'src/app/model/project';
import { DataService } from 'src/app/services/data.service';
import { RestService } from 'src/app/services/rest.service';
import { Role } from 'src/app/model/role';
import { Skill } from 'src/app/model/skill';


@Component({
  selector: 'app-view-project',
  templateUrl: './view-project.page.html',
  styleUrls: ['./view-project.page.scss'],
})

export class ViewProjectPage {
  private id: string;
  project: Project;
  organization:Organization;
  loading: boolean;
  skill: Skill;

  constructor(
    private route: ActivatedRoute,
    private menuCtrl: MenuController,
    public nav: NavController,
    private restService: RestService,
    public dataSerivice: DataService,
    private actionSheetCtrl: ActionSheetController,
    private alertController:AlertController,
    private toastController:ToastController
  ) {
    this.id = this.route.snapshot.params["id"];
    this.loading = true;
    this.load().then(
      ()=> {
        this.loading = false;
      }
    )
  }

  ionViewDidEnter() {
    this.menuCtrl.enable(false);
  }

  async load() {
    this.project = await this.restService.getProject(this.id);
    this.organization = await this.restService.getOrganization(this.project.organizationId);
  }

  goBack() {
    this.nav.navigateBack(["/list-of-projects"], { queryParams: { 'refresh': 1 } });
  }

  modify() {
    this.nav.navigateForward(['/modify-project', { "id": this.project.id }]);
  }

  delete() {
    this.restService.deleteProject(this.id);
    this.goBack();
  }

  public async reload(event?) {
    const newProject = await this.restService.getProject(this.id);
    this.project = newProject; 
    event.target.complete();
  }

  async showActionSheet() {
    const actionSheet = await this.actionSheetCtrl.create({
      header: 'Project',
      cssClass: 'my-custom-class',
      buttons: this.getButtons()
    });
    await actionSheet.present();
  }

  createSkillInput() {
    const theNewInputs = [];
    var i:number = 1;
    this.project.neededSkills .forEach(element => {
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

  async submit(){
    const add = await this.alertController.create({
      cssClass: 'my-custom-class',
      header: 'Submit a Role',
      message: '',
      inputs: this.createSkillInput(),
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
                this.skill = this.project.neededSkills.find(obj=> obj==data);
                this.restService.submit(this.id, new Role(this.dataSerivice.getUserMail(), this.skill, false))
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

    // azioni per i membri dell'organizzazione
    // TODO da implementare perche non ho l'organizzazione

    // azioni per il creatore del progetto e creatore dell'organizzazione
    //if (this.dataSerivice.hasProjectCreatorPermission(this.project) || this.dataSerivice.hasOrganizationCreatorPermission(this.restService.getOrganization(this.project.organizationId))) {
    if (this.dataSerivice.hasProjectCreatorPermission(this.project)) {
      buttons = buttons.concat([
        {
          text: 'Delete',
          role: 'destructive',
          icon: 'trash',
          handler: () => {
            this.restService.deleteProject(this.project.id);
          }
        }, {
          text: 'Close',
          icon: 'close-outline',
          handler: () => {
            this.restService.closeProject(this.project.id);
          }
        }, {
          text: 'Edit',
          icon: 'create-outline',
          handler: () => {
            this.nav.navigateForward(["/modify-project", { "id": this.project.id }]);
          }
        }
      ]);
    }

    // azioni per user non creatore del progetto o creatore dell'organizzazione
    if (this.dataSerivice.isUserLogged && !this.dataSerivice.hasProjectCreatorPermission(this.project)){
      buttons = buttons.concat([
        {
          text: 'Submit',
          icon: 'chevron-down-outline',
          handler: () => {
            this.submit();
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

}


