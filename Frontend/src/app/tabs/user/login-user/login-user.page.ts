import { DataService } from 'src/app/services/data.service';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MenuController, NavController } from '@ionic/angular';
import { RestService } from 'src/app/services/rest.service';



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
    private navCtrl: NavController,
    private dataService: DataService,
    private restService: RestService,
  ) {
    this.validations_form = this.formBuilder.group({
      email: ['', Validators.compose([
        Validators.required,
        Validators.pattern('^[A-Za-z0-9._%+-]{3,}@[a-zA-Z]{3,}([.]{1}[a-zA-Z]{2,}|[.]{1}[a-zA-Z]{2,}[.]{1}[a-zA-Z]{2,})')
      ])],
    });
  }

  ionViewDidEnter() {
    this.menuCtrl.enable(false);
  }

  onSubmit() {
    this.restService.getUser(this.email).then(
      user=>{
        this.dataService.setUser(user);
        this.restService.presentToast("Accesso eseguito come "+user.name);
      }
    );
    this.navCtrl.navigateBack(['/home'], { queryParams: { 'refresh': 1 } });
  }

}
