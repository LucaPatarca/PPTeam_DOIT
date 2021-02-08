import { Component } from '@angular/core';
import { Organization } from 'src/app/model/organization';
import { Router, ActivatedRoute } from "@angular/router";
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { MenuController, ToastController } from '@ionic/angular';
import { DataService } from 'src/app/services/data.service';
import { GlobalsService } from 'src/app/services/globals.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-modify-organization',
  templateUrl: './modify-organization.page.html',
  styleUrls: ['./modify-organization.page.scss'],
})
export class ModifyOrganizationPage {

  organization: Organization;
  validations_form: FormGroup;
  title: string;
  description: string;
  validation_messages = {
    'name': [
      { type: 'required', message: 'Name is required.' }
    ],
    'description': [
      { type: 'required', message: 'description is required.' }
    ],
  };

  constructor(
    private route: ActivatedRoute,
    public router: Router,
    private menuCtrl: MenuController,
    public formBuilder: FormBuilder,
    private http: HttpClient,
    private dataService: DataService,
    private globals: GlobalsService,
    private toastCtrl: ToastController
  ) {
    this.validations_form = this.formBuilder.group({

      title: ['', Validators.required],
      description: [Validators.required],
    });
    const id = this.route.snapshot.params['id'];
    this.organization = dataService.getOrganization(id);
  }

  ionViewWillEnter() {
    this.menuCtrl.enable(false);
  }

  save() {
    this.organization.name = this.title;
    this.organization.description = this.description;
    this.http.put(this.globals.modifyOrganizationApiUrl, this.organization, { headers: new HttpHeaders(), responseType: 'json' })
      .subscribe(
        async res => {
          console.log('Successfully saved Organization with Id: ' + this.organization.id);
          this.dataService.updateOrganization(res as Organization);

          const toast = await this.toastCtrl.create({
            message: 'Oranizazione Modificata.',
            duration: 2000
          });
          toast.present();

          this.router.navigate(['/view-organization', { 'id': res['id'] }]);
        },
        async err => {
          const toast = await this.toastCtrl.create({
            message: 'Oranizazione Modificata.',
            duration: 2000
          });
          toast.present();
        }
      );
  }
}
