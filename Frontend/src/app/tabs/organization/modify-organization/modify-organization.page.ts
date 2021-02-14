import { Component } from '@angular/core';
import { Organization } from 'src/app/model/organization';
import { Router, ActivatedRoute } from "@angular/router";
import { MenuController, NavController } from '@ionic/angular';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { RestService } from 'src/app/services/rest.service';

@Component({
  selector: 'app-modify-organization',
  templateUrl: './modify-organization.page.html',
  styleUrls: ['./modify-organization.page.scss'],
})
export class ModifyOrganizationPage {

  organization: Organization;
  id: string;
  validations_form: FormGroup;
  title: string;
  description: string;
  validation_messages = {
    'title': [
      { type: 'required', message: 'Name is required.' }
    ],
    'description': [
      { type: 'required', message: 'description is required.' }
    ],
  };

  constructor(
    private route: ActivatedRoute,
    private menuCtrl: MenuController,
    public formBuilder: FormBuilder,
    private restService: RestService,
    private navCtrl: NavController,
  ) {
    this.validations_form = this.formBuilder.group({

      title: ['', Validators.required],
      description: [Validators.required],
    });
    this.id = this.route.snapshot.params['id'];
  }

  ionViewDidEnter() {
    this.menuCtrl.enable(false);
    this.loadOrganization();
    //todo non era meglio se prendevi l'organizzazione direttamente dalla schermata precedente coglione?!
  }

  async loadOrganization(){
    try{
      this.organization = await this.restService.getOrganization(this.id);
    } catch(e){
      console.log(e);
      //todo gestire
    }
    
  }

  async save() {
    this.organization.name = this.title;
    this.organization.description = this.description;
    const newOrganization = await this.restService.modifyOrganization(this.organization);
    this.navCtrl.navigateBack(["/view-organization", {'id': newOrganization.id }])
  }
}
