import { DataService } from 'src/app/services/data.service';
import { MenuController, NavController, AlertController } from '@ionic/angular';
import { Component } from '@angular/core';
import { Validators, FormBuilder, FormGroup } from '@angular/forms';
import { User } from 'src/app/model/user';
import { RestService } from 'src/app/services/rest.service';

@Component({
  selector: 'app-create-user',
  templateUrl: './create-user.page.html',
  styleUrls: ['./create-user.page.scss'],
})
export class CreateUserPage {
  validations_form: FormGroup;
  name: string;
  email: string;
  secret:string;
  confirm:string;
  validation_messages = {
    'name': [
      { type: 'required', message: 'Name is required.' }
    ],
    'email': [
      { type: 'required', message: 'Email is required.' },
      { type: 'pattern', message: 'Please enter a valid email.' }
    ],
    'secret': [
      { type: 'required', message: 'Password is required.' },
    ],
    'confirm': [
      { type: 'required', message: 'Repeat password is required.' },
    ],
  };

  constructor(private menuCtrl: MenuController,
    public formBuilder: FormBuilder,
    private nav: NavController,
    private restService: RestService,
    private dataService: DataService,
    private alert:AlertController,
    ) {
    this.validations_form = this.formBuilder.group({
      name: ['', Validators.required],
      email: ['', Validators.compose([
        Validators.required,
        Validators.pattern('^[A-Za-z0-9._%+-]{3,}@[a-zA-Z]{3,}([.]{1}[a-zA-Z]{2,}|[.]{1}[a-zA-Z]{2,}[.]{1}[a-zA-Z]{2,})')
      ])],
      secret: ['', Validators.compose([
        Validators.required,
      ])],
      confirm: ['', Validators.compose([
        Validators.required,
      ])],
    });
  }

  ionViewDidEnter() {
    this.menuCtrl.enable(false);
  }

  async onSubmit() {
    var newUser = new User();
    newUser.mail = this.email;
    newUser.name = this.name;
    newUser.secret = this.secret;
    if(this.secret  ==this.confirm){
      this.restService.createUser(newUser).then(
        user=>{
          this.nav.navigateBack(['/home'], { queryParams: { 'refresh': 1 } });
        }
      );
    }else{
      const alert = await this.alert.create({
        header: 'Error',
        message: 'Password and confirm password dont match.',
        buttons: ['Okay']
      });
      await alert.present();
    }
  }
}
