import { DataService } from 'src/app/services/data.service';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MenuController, NavController } from '@ionic/angular';
import { RestService } from 'src/app/services/rest.service';
import { User } from 'src/app/model/user';



@Component({
  selector: 'app-login-user',
  templateUrl: './login-user.page.html',
  styleUrls: ['./login-user.page.scss'],
})
export class LoginUserPage {

  validations_form: FormGroup;
  mail: string;
  secret: string;
  validation_messages = {
    'mail': [
      { type: 'required', message: 'Email is required.' },
      { type: 'pattern', message: 'Please enter a valid email.' }
    ],
    'secret': [
      { type: 'required', message: 'Password is required.' } 
    ]
  };

  constructor(private menuCtrl: MenuController,
    public formBuilder: FormBuilder,
    private navCtrl: NavController,
    private dataService: DataService,
    private restService: RestService,
  ) {
    this.validations_form = this.formBuilder.group({
      mail: ['', Validators.compose([
        Validators.required,
        Validators.pattern('^[A-Za-z0-9._%+-]{3,}@[a-zA-Z]{3,}([.]{1}[a-zA-Z]{2,}|[.]{1}[a-zA-Z]{2,}[.]{1}[a-zA-Z]{2,})')
      ])],
      secret:['',Validators.compose([Validators.required])]
    });
  }

  ionViewDidEnter() {
    this.menuCtrl.enable(false);
  }

  onSubmit() {
    let user:User = new User();
    user.mail = this.mail;
    user.secret = this.secret;
    this.restService.login(user).then(
      user=>{
        this.navCtrl.navigateBack(['/home'], { queryParams: { 'refresh': 1 } });
        this.restService.presentToast("Accesso eseguito come "+(user as unknown as User).name);
      }
    );
  }

}
