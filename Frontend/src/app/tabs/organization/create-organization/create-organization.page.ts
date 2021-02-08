import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MenuController, NavController, ToastController } from '@ionic/angular';
import { Organization } from 'src/app/model/organization';
import { DataService } from 'src/app/services/data.service';
import { GlobalsService } from 'src/app/services/globals.service';

@Component({
  selector: 'app-create-organization',
  templateUrl: './create-organization.page.html',
  styleUrls: ['./create-organization.page.scss'],
})
export class CreateOrganizationPage {

  validations_form: FormGroup;
  name: string;
  description: string;
  validation_messages = {
    'name': [
      { type: 'required', message: 'name is required.' },
    ],
    'email': [
      { type: 'required', message: 'Email is required.' },
      { type: 'pattern', message: 'Please enter a valid email.' }
    ]
  };

  constructor(private menuCtrl: MenuController,
    public formBuilder: FormBuilder,
    private http: HttpClient,
    private dataService: DataService,
    private navCtrl: NavController,
    private globals: GlobalsService,
    private toastCtrl: ToastController
  ) {
    this.validations_form = this.formBuilder.group({
      name: ['', Validators.required],
      description: ['', Validators.required],
    });
  }

  ionViewWillEnter() {
    this.menuCtrl.enable(false);
  }

  createOrganization() {
    // metodo per effettuare una chiamata post
    const newOrganization = {
      "name": this.name,
      "description": this.description,
      "expertsMails": [],
      "membersMails": [],
      "creatorMail": this.dataService.getUser(),
      "collaboratorsMails": {},
    }

    this.http.post(this.globals.createOrganizationApiUrl, newOrganization, { headers: new HttpHeaders(), responseType: 'json' }).subscribe(
      async res => {
        console.log('Successfully created new organization');
        this.dataService.addOrganization(res as Organization);
        const toast = await this.toastCtrl.create({
          message: 'Organizzazione Creata.',
          duration: 2000
        });
        toast.present();

        this.navCtrl.navigateRoot(["/home"], { queryParams: { 'refresh': 1 } });
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

}
