import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from "@angular/router";
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { MenuController, NavController, ToastController } from '@ionic/angular';
import { Project } from 'src/app/model/project';
import { DataService } from 'src/app/services/data.service';
import { GlobalsService } from 'src/app/services/globals.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-modify-project',
  templateUrl: './modify-project.page.html',
  styleUrls: ['./modify-project.page.scss'],
})
export class ModifyProjectPage {
  project: Project;
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

  constructor(
    private route: ActivatedRoute,
    public nav: NavController,
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
  }

  ionViewDidlEnter() {
    this.menuCtrl.enable(false);
  }

  save() {
    this.project.name = this.title;
    this.project.description = this.description;
    this.http.put(this.globals.modifyProjectApiUrl, this.project, { headers: new HttpHeaders(), responseType: 'json' })
      .subscribe(
        async res => {
          console.log('Successfully saved Project with Id: ' + this.project.id);
          const toast = await this.toastCtrl.create({
            message: 'Progetto Modificato.',
            duration: 2000
          });
          toast.present();

          this.goBack(res['id']);
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

  public goBack(id: string) {
    this.nav.navigateBack(['/view-project', { 'id': id }]);
  }

}
