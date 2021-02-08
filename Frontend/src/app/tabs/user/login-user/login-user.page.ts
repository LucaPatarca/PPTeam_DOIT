import { DataService } from 'src/app/services/data.service';
import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MenuController, NavController, ToastController } from '@ionic/angular';
import { GlobalsService } from 'src/app/services/globals.service';
import { AlertController } from '@ionic/angular';



@Component({
  selector: 'app-login-user',
  templateUrl: './login-user.page.html',
  styleUrls: ['./login-user.page.scss'],
})
export class LoginUserPage {

  validations_form: FormGroup;
  email: string;
  validation_messages = {
    'email': [
      { type: 'required', message: 'Email is required.' },
      { type: 'pattern', message: 'Please enter a valid email.' }
    ],
  };

  constructor(private menuCtrl: MenuController,
    public formBuilder: FormBuilder,
    private http: HttpClient,
    private globals: GlobalsService,
    private navCtrl: NavController,
    private dataService: DataService,
    private toastCtrl: ToastController,
  ) {
    this.validations_form = this.formBuilder.group({
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
    this.http.get(this.globals.userExistApiUrl + this.email).subscribe(
      async res => {
        if (res == true) {
          this.dataService.setUser(this.email);

          const toast = await this.toastCtrl.create({
            message: 'Accesso eseguito come ' + this.email,
            duration: 2000
          });
          toast.present();

          this.goBack();
        } else {
          const toast = await this.toastCtrl.create({
            message: "l'email non esiste",
            duration: 2000
          });
          toast.present();
        }
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

  goBack() {
    this.navCtrl.navigateBack(['/home'], { queryParams: { 'refresh': 1 } });
  }

}
