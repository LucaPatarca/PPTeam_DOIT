import { Organization } from './../../../../model/organization';
import { ActivatedRoute } from '@angular/router';
import { Component } from '@angular/core';
import { ActionSheetController, AlertController, NavController, ToastController } from '@ionic/angular';
import { Project } from 'src/app/model/project';
import { DataService } from 'src/app/services/data.service';
import { RestService } from 'src/app/services/rest.service';
import { Role } from 'src/app/model/role';
import { Skill } from 'src/app/model/skill';
import { User } from 'src/app/model/user';


@Component({
  selector: 'app-view-project',
  templateUrl: './view-project.page.html',
  styleUrls: ['./view-project.page.scss'],
})

export class ViewProjectPage {
  private id: string;
  project: Project;
  organization: Organization;
  creator: User;
  loading: boolean;
  userAvailableSkillsInput: any;
  errorLoading: boolean;

  constructor(
    private route: ActivatedRoute,
    public nav: NavController,
    private restService: RestService,
    public dataService: DataService,
    private actionSheetCtrl: ActionSheetController,
    private alertController: AlertController,
    private toastController: ToastController
  ) {
    this.id = this.route.snapshot.params["id"];
    this.loading = true;
    this.organization = null;
    this.creator = null;
    this.errorLoading = false;
    this.load().then(() => {
        this.createSkillInput();
        this.loading = false;
      }).catch(err=>{
        this.errorLoading = true;
        this.loading = false;
      });
    this.userAvailableSkillsInput = new Array();
  }

  async load() {
    this.project = await this.restService.getProject(this.id);
    this.organization = await this.restService.getOrganization(this.project.organizationId);
    this.creator = await this.restService.getUser(this.project.creatorMail);
  }

  goBack() {
    this.nav.navigateBack(["/tabs/list-of-projects"], { queryParams: { 'refresh': 1 } });
  }

  modify() {
    this.dataService.modify = this.project;
    this.nav.navigateForward(['/tabs/list-of-projects/view-project/edit-project']);
  }

  delete() {
    this.restService.deleteProject(this.id);
    this.goBack();
  }

  public async reload(event?) {
    this.restService.getProject(this.id).then(project=>{
      this.project = project;
      this.errorLoading = false;
      this.createSkillInput();
    }).catch(err=>{
      this.errorLoading = true;
    }).finally(()=>{
      if (event)
        event.target.complete();
    });
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
    this.project.neededSkills.forEach(element => {
      const userSkill = this.dataService.getUser().skills.find(it => it.name.toUpperCase() == element.name.toUpperCase());
      var alreadyInTeam: Role;
      var alreadyInCandidates: Role;
      if (userSkill) {
        alreadyInTeam = this.project.team.find(it =>
          it.skill.name.toUpperCase() == userSkill.name.toUpperCase() && it.userMail == this.dataService.getUser().mail);
        alreadyInCandidates = this.project.candidates.find(it =>
          it.skill.name.toUpperCase() == userSkill.name.toUpperCase() && it.userMail == this.dataService.getUser().mail);
      };
      if (userSkill && userSkill.level >= element.level && alreadyInTeam == undefined && alreadyInCandidates == undefined) {
        theNewInputs.push(
          {
            type: 'radio',
            label: element.name + ' ' + element.level,
            value: element,
            checked: false
          }
        );
      }
    });
    this.userAvailableSkillsInput = theNewInputs;
  }

  async submit() {
    const add = await this.alertController.create({
      cssClass: 'my-custom-class',
      header: 'Submit a Role',
      message: '',
      inputs: this.userAvailableSkillsInput,
      buttons: [
        {
          text: 'cancel',
        }, {
          text: 'add',
          handler: async data => {
            var skill = new Skill();
            if (data == null) {
              const toast = await this.toastController.create({
                message: 'Campo Skill non selezionato',
                duration: 2000
              });
              toast.present();
            } else {
              const role = new Role(this.dataService.getUserMail(), data, data.level >= 10);
              this.restService.submit(this.id,role)
                .then(val => {
                  if (val){
                    this.restService.presentToast("Ti sei iscritto a questo progetto!");
                    this.project.candidates.push(role);
                    this.createSkillInput();
                  }
                  else
                    this.restService.presentToast("C'è stato un errore durante l'iscrizione");
                }).catch(err => {
                  this.restService.presentToast("C'è stato un errore durante l'iscrizione");
                });
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
    if (this.dataService.hasProjectCreatorPermission(this.project)) {
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
      if(this.project.closed==false){
        if(this.project.neededSkills)
        buttons = buttons.concat([
          {
            text: 'Close',
            icon: 'close-outline',
            handler: () => {
              this.restService.closeProject(this.project.id)
                  .then(val => {
                    this.restService.presentToast("Progetto chiuso");
                    this.project.closed=true;
                    this.load();
                  }).catch(err => {
                this.restService.presentToast("C'è stato un errore durante la chiusura");
              });
            }
          }, {
            text: 'Edit',
            icon: 'create-outline',
            handler: () => {
              this.modify();
            }
          } 
      ])
      }
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

  acceptCandidate(role: Role, slidingItem: any) {
    const index = this.project.candidates.indexOf(role);
    const indexNeededSkill = this.project.neededSkills.indexOf(role.skill);
    this.restService.acceptCandidate(this.project.id, role).then(
      res => {
        if (res) {
          this.project.team.push(role);
          this.project.neededSkills.splice(indexNeededSkill,1);
        } else {
          this.project.candidates.splice(index, 0, role);
        }
      }
    ).catch(err => {
      this.project.candidates.splice(index, 0, role);
    });
    this.project.candidates.splice(index, 1);
    slidingItem.close();
  }

  rejectCandidate(role: Role, slidingItem: any) {
    const index = this.project.candidates.indexOf(role);
    this.restService.rejectCandidate(this.project.id, role).then(
      res => {
        if (!res) {
          this.project.candidates.splice(index, 0, role);
        }
        this.createSkillInput();
      }
    ).catch(err => {
      this.project.candidates.splice(index, 0, role);
    });
    this.project.candidates.splice(index, 1);
    slidingItem.close();
  }

  getCandidates(): Role[] {
    return this.project.candidates.filter(it => this.dataService.hasTeamManagerPermission(this.organization, this.project, it.skill));
  }

  removeTeamMember(role:Role,slidingItem:any){
    const index = this.project.team.indexOf(role);
    this.restService.removeTeamMember(this.project.id,role).then(
      res=>{
        if(!res){
          this.project.team.splice(index,0,role);
        }
      }
    ).catch(err=>{
      this.project.team.splice(index,0,role);
    });
    this.project.team.splice(index, 1);
    this.project.neededSkills.push(role.skill);
    slidingItem.close();
    this.restService.presentToast("Membro del team rimosso con successo");
  }

  viewOrganization(organizationId:string){
    this.nav.navigateForward(['/tabs/list-of-organizations/view-organization', { "id": organizationId }]);
  }

}


