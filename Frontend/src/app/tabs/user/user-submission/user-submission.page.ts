import { Role } from './../../../model/role';
import { RestService } from 'src/app/services/rest.service';

import { Component } from '@angular/core';
import { NavController, AlertController } from '@ionic/angular';
import { UserSubmissionInformation } from 'src/app/model/UserSubmissionInformation';
import { DataService } from 'src/app/services/data.service';


@Component({
  selector: 'app-user-submission',
  templateUrl: './user-submission.page.html',
  styleUrls: ['./user-submission.page.scss'],
})

export class UserSubmissionPage {
  
  userSubmissions:UserSubmissionInformation[];
  loading: boolean;
  page = 0;

  constructor(
    private nav: NavController,
    private dataSerivice: DataService,
    private restService:RestService,
    private alertController:AlertController,
  ) {
    this.userSubmissions = new Array();
    this.loading = true;
    this.load().then(()=>{
      this.loading = false;
    });
  }

  ionViewDidEnter() {
  }

  async load() {
    this.userSubmissions=new Array();
    const submission = await this.restService.getUserSubmissions();
    this.userSubmissions = this.userSubmissions.concat(submission);
  }

  goBack() {
    this.nav.navigateBack(["/home"], { queryParams: { 'refresh': 1 } });
  }

  async removeSubmission(UserSubmission : UserSubmissionInformation,role:Role){
    const add = await this.alertController.create({
      cssClass: 'my-custom-class',
      header: 'Vuoi rimuovere la submit ?',
      message: '',
      buttons: [
        {
          text: 'no',
        }, {
          text: 'si',
          handler: async () => {
            console.log(await this.restService.rejectSubmission(UserSubmission,role));
            
          }
        }
      ]
    });
    await add.present();
  }

  modify() {
  }

  delete() {
    
  }
}


