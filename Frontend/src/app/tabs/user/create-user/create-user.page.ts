import { DataService } from 'src/app/services/data.service';
import { GlobalsService } from 'src/app/services/globals.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { MenuController, AlertController, NavController, Platform, ToastController } from '@ionic/angular';
import { Component } from '@angular/core';
import { Validators, FormBuilder, FormGroup } from '@angular/forms';
import { User } from 'src/app/model/user';

@Component({
  selector: 'app-create-user',
  templateUrl: './create-user.page.html',
  styleUrls: ['./create-user.page.scss'],
})
export class CreateUserPage {

  validations_form: FormGroup;
  name: string;
  email: string;
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
    public formBuilder: FormBuilder,
    private http: HttpClient,
    private globals: GlobalsService,
    private nav: NavController,
    private dataService: DataService,
    private toastCtrl: ToastController,
  ) {
    this.validations_form = this.formBuilder.group({
      name: ['', Validators.required],
      email: ['', Validators.compose([
        Validators.required,
        Validators.pattern('^[A-Za-z0-9._%+-]{3,}@[a-zA-Z]{3,}([.]{1}[a-zA-Z]{2,}|[.]{1}[a-zA-Z]{2,}[.]{1}[a-zA-Z]{2,})')
      ])],
    });
  }

  ionViewWillEnter() {
    this.menuCtrl.enable(false);
  }

  onSubmit() {
    var newUser = { "name": this.name, "mail": this.email };
    this.http.post(this.globals.createUserApiUrl, newUser, { headers: new HttpHeaders(), responseType: 'json' }).subscribe(
      async res => {
        console.log('Successfully created new User');
        this.dataService.setUser(res as User)
        const toast = await this.toastCtrl.create({
          message: "Utente " + this.name + " creato con successo",
          duration: 2000,
        });
        this.goBack();
        toast.present();
      },
      async err => {
        const toast = await this.toastCtrl.create({
          message: err.error,
          duration: 2000,
        });
        toast.present();
      });
  }

  goBack() {
    this.nav.navigateBack(['/home'], { queryParams: { 'refresh': 1 } });
  }
}
