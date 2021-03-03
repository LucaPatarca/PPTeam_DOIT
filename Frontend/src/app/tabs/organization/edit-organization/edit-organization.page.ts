import { Component, OnInit } from '@angular/core';
import { Organization } from 'src/app/model/organization';
import { Router, ActivatedRoute } from "@angular/router";
import { LoadingController, MenuController, NavController } from '@ionic/angular';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { RestService } from 'src/app/services/rest.service';

@Component({
  selector: 'app-edit-organization',
  templateUrl: './edit-organization.page.html',
  styleUrls: ['./edit-organization.page.scss'],
})
export class EditOrganizationPage implements OnInit {

  organization: Organization;
  id: string;
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
    this.loading = true;
  }

  ngOnInit(): void {
    this.loadOrganization().then(value => {
      this.loading = false;
    });
  }

  async ionViewDidEnter() {
    this.menuCtrl.enable(false);
  }

  async loadOrganization() {
    try {
      this.organization = await this.restService.getOrganization(this.id);
    } catch (e) {
      console.log(e);
      //todo gestire
    }

  }

  async save() {
    const newOrganization = await this.restService.modifyOrganization(this.organization);
    this.navCtrl.navigateBack(["/view-organization", { 'id': newOrganization.id }])
  }
}
