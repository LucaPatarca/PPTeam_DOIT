import { Organization } from 'src/app/model/organization';
import { DataService } from 'src/app/services/data.service';
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
  
  page = 0;
  loading: boolean;
  users:User[];
  textNoExpert = "Nessuno Esperto disponibile";
  organizationId: string;
  skill: Skill;

  constructor(
    private restService: RestService,
    private navCtrl:NavController,
    private route: ActivatedRoute,
    private alertController:AlertController,
    private toastController:ToastController,
    private dataService:DataService,
  ) {
    this.users = new Array();
    this.organizationId = this.route.snapshot.params["id"];
    this.loading = true;
    this.loadUser().then(
      ()=>{
        this.loading = false;
      }
    );
   }

   async loadUser(event?){
    const newUsers = await this.restService.getExpertPage(this.page);
    let usersApp:User[] = new Array();
    usersApp = this.users.concat(newUsers);
    usersApp.forEach(async element => {
    this.isPresent(element);
    });
    if(event){
      event.target.complete();
    }
  }

  loadMore(event: any) {
    this.page++;
    this.loadUser(event);
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
    if(!element.expertInOrganization.includes(this.organizationId)){
      if(element.level==10){
        theNewInputs.push(
        {
          type: 'radio',
          label: element.name+' '+element.level,
          value: element,
          checked: false
        }
      );
      i++;
      }
    }
    });
    return theNewInputs;
  }

  goBack() {
    this.navCtrl.navigateBack(["/list-of-organizations"], { queryParams: { 'refresh': 1 } });
  }

  async isPresent(user:User){
    let check=false;
    (this.dataService.getOrganization() as unknown as Organization).membersMails.forEach(element => {
      if(element == user.mail){
        check=true;
      }
    });
    if(check==false){
    this.users.push(user);
    }
  }

}
