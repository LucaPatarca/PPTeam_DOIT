
import { ActivatedRoute } from '@angular/router';
import { Component } from '@angular/core';
import { ActionSheetController, AlertController, MenuController, NavController, ToastController } from '@ionic/angular';
import { Project } from 'src/app/model/project';
import { DataService } from 'src/app/services/data.service';
import { RestService } from 'src/app/services/rest.service';
import { Role } from 'src/app/model/role';
import { Skill } from 'src/app/model/skill';
import { Organization } from 'src/app/model/organization';


@Component({
  selector: 'app-user-submission',
  templateUrl: './user-submission.page.html',
  styleUrls: ['./user-submission.page.scss'],
})

export class UserSubmissionPage {
  roles:Role[];
  projects:Project[];
  organizations:Organization[];
  loading:boolean;

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
    
  }

  goBack() {
    this.nav.navigateBack(["/home"], { queryParams: { 'refresh': 1 } });
  }

  modify() {
  }

  delete() {
    
  }
}


