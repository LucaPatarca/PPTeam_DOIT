import { DataService } from 'src/app/services/data.service';
import { Component, OnInit } from '@angular/core';
import { Organization } from 'src/app/model/organization';
import {  NavController } from '@ionic/angular';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { RestService } from 'src/app/services/rest.service';

@Component({
  selector: 'app-edit-organization',
  templateUrl: './edit-organization.page.html',
  styleUrls: ['./edit-organization.page.scss'],
})
export class EditOrganizationPage implements OnInit {

  organization: Organization;
  validations_form: FormGroup;
  loading: boolean;
  validation_messages = {
    'title': [
      { type: 'required', message: 'Name is required.' }
    ],
    'description': [
      { type: 'required', message: 'description is required.' }
    ],
  };

  constructor(
    public formBuilder: FormBuilder,
    private restService: RestService,
    private navCtrl: NavController,
    private dataService:DataService,
  ) {
    this.validations_form = this.formBuilder.group({

      title: ['', Validators.required],
      description: [Validators.required],
    });
    this.organization = this.dataService.modify as Organization;
    this.loading = true;
  }

  ngOnInit(): void {
    this.loadOrganization();
  }

  async loadOrganization() {
    this.organization = this.dataService.modify as Organization;
    this.loading = false;
  }

  async save() {
    this.restService.modifyOrganization(this.organization).then(org=>{
      this.navCtrl.navigateBack(["/view-organization", { 'id': org.id }]);
    }).catch(err=>{
      this.restService.presentToast("impossibile modificare l'organizzazione");
    });
  }
}
