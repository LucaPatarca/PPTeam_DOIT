import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component } from '@angular/core';
import { MenuController, NavController, ToastController } from '@ionic/angular';
import { GlobalsService } from 'src/app/services/globals.service';
import { DataService } from 'src/app/services/data.service';
import { Validators, FormBuilder, FormGroup } from '@angular/forms';
import { Project } from 'src/app/model/project';



@Component({
  selector: 'app-create-project',
  templateUrl: './create-project.page.html',
  styleUrls: ['./create-project.page.scss'],
})
export class CreateProjectPage {

  validations_form: FormGroup;
  title: string;
  description: string;
  validation_messages = {
    'name': [
      { type: 'required', message: 'Name is required.' }
    ],
    'email': [
      { type: 'required', message: 'Email is required.' },
      { type: 'pattern', message: 'Please enter a valid email.' }
    ],
  };

  constructor(private menuCtrl: MenuController,
    private http: HttpClient,
    public formBuilder: FormBuilder,
    private navCtrl: NavController,
    public dataService: DataService,
    private toastCtrl: ToastController,
    private globals: GlobalsService) {
    this.validations_form = this.formBuilder.group({
      title: ['', Validators.required],
      description: ['', Validators.required],
    });
  }

  ionViewDidEnter() {
    this.menuCtrl.enable(false);
  }

  createProject() {
    // metodo per effettuare una chiamata post
    var newProject = {
      "name": this.title,
      "description": this.description,
      "organizationId": this.dataService.selectedOrganization,
      "creatorMail": this.dataService.getUser().mail,
      "neededSkills": [],
      "closed": false,
      "team": [],
      "candidates": []
    };

    this.http.post(this.globals.createProjectApiUrl, newProject, { headers: new HttpHeaders(), responseType: 'json' }).subscribe(
      async res => {
        console.log('Successfully created new project');
        this.menuCtrl.enable(true);
        const toast = await this.toastCtrl.create({
          message: 'Progetto Creato',
          duration: 2000
        });
        toast.present();

        this.goToProjectsList();
      },
      async err => {
        const toast = await this.toastCtrl.create({
          message: err.error,
          duration: 2000
        });
        toast.present();
      }
    );
  }

  goToProjectsList() {
    this.navCtrl.navigateRoot(['/list-of-projects'], { queryParams: { 'refresh': 1 } });
  }
}
