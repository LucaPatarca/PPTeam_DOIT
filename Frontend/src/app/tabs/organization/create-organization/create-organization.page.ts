import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import {  NavController } from '@ionic/angular';
import { Organization } from 'src/app/model/organization';
import { DataService } from 'src/app/services/data.service';
import { RestService } from 'src/app/services/rest.service';

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
    'description': [
      { type: 'required', message: 'description is required.' },
    ]
  };

  constructor(
    public formBuilder: FormBuilder,
    private dataService: DataService,
    private restService: RestService,
    private navCtrl: NavController,
  ) {
    this.validations_form = this.formBuilder.group({
      name: ['', Validators.required],
      description: ['', Validators.required],
    });
  }

  async createOrganization() {
    const newOrganization = new Organization(
      this.name,
      this.description,
      this.dataService.getUser().mail,
    );

    this.restService.createOrganization(newOrganization).then(res=>{
      this.navCtrl.navigateRoot(["/tabs/list-of-organizations"], { queryParams: { 'refresh': 1 } });
    }).catch(err=>{
      this.restService.presentToast("Impossibile creare l'organizzazione");
    });
  }

}
