import { Organization } from './../../../../model/organization';
import { ActivatedRoute } from '@angular/router';
import { Component } from '@angular/core';
import { ActionSheetController, AlertController, MenuController, NavController, ToastController } from '@ionic/angular';
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
  organization:Organization;
  creator: User;
  loading: boolean;

  constructor(
    private route: ActivatedRoute,
    private menuCtrl: MenuController,
    public nav: NavController,
    private restService: RestService,
    public dataService: DataService,
    private actionSheetCtrl: ActionSheetController,
    private alertController:AlertController,
    private toastController:ToastController
  ) {
    this.id = this.route.snapshot.params["id"];
    this.loading = true;
    this.organization = null;
    this.creator = null;
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
    this.creator = await this.restService.getUser(this.project.creatorMail);
  }

  goBack() {
    this.nav.navigateBack(["/list-of-projects"], { queryParams: { 'refresh': 1 } });
  }

  modify() {
    this.nav.navigateForward(['/edit-project', { "id": this.project.id }]);
  }

  delete() {
    this.restService.deleteProject(this.id);
    this.goBack();
  }

  public async reload(event?) {
    const newProject = await this.restService.getProject(this.id);
    this.project = newProject;
    if(event)
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
            var skill = new Skill();
            if (data==null) {
              const toast = await this.toastController.create({
                message: 'Campo Skill non selezionato',
                duration: 2000
              });
              toast.present();
            } else {
                skill = this.project.neededSkills.find(obj=> obj==data);
                this.restService.submit(this.id, new Role(this.dataService.getUserMail(), skill, false))
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
    if (this.dataService.hasProjectCreatorPermission(this.project)) {
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
            this.modify();
          }
        }
      ]);
    }

    // azioni per user non creatore del progetto o creatore dell'organizzazione
    if (this.dataService.isUserLogged && !this.dataService.hasProjectCreatorPermission(this.project)){
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

  acceptCandidate(role: Role, slidingItem: any){
    const index = this.project.candidates.indexOf(role);
    this.restService.acceptCandidate(this.project.id,role).then(
      res=>{
        if(res){
          this.project.team.push(role);
        } else{
          this.project.candidates.splice(index,0,role);
        }
      }
    ).catch(err=>{
      this.project.candidates.splice(index,0,role);
    });
    this.project.candidates.splice(index, 1);
    slidingItem.close();
  }

  rejectCandidate(role: Role, slidingItem: any){
    const index = this.project.candidates.indexOf(role);
    this.restService.rejectCandidate(this.project.id,role).then(
      res=>{
        if(!res){
          this.project.candidates.splice(index,0,role);
        }
      }
    ).catch(err=>{
      this.project.candidates.splice(index,0,role);
    });
    this.project.candidates.splice(index, 1);
    slidingItem.close();
  }

  getCandidates(): Role[]{
    return this.project.candidates.filter(it=>this.dataService.hasTeamManagerPermission(this.organization,this.project,it.skill));
  }

}


