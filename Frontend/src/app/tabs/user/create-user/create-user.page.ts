import { DataService } from 'src/app/services/data.service';
import { MenuController, NavController } from '@ionic/angular';
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
    private nav: NavController,
    private restService: RestService,
    private dataService: DataService,
  ) {
    this.validations_form = this.formBuilder.group({
      name: ['', Validators.required],
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
    var newUser = new User();
    newUser.mail = this.email;
    newUser.name = this.name;
    this.restService.createUser(newUser).then(
      user=>{
        this.dataService.setUser(user);
      }
    );
    
    this.nav.navigateBack(['/home'], { queryParams: { 'refresh': 1 } });
  }
}
