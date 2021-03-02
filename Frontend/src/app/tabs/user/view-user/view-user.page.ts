import { Skill } from './../../../model/skill';
import { UserSubmissionInformation } from './../../../model/UserSubmissionInformation';
import { DataService } from 'src/app/services/data.service';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AlertController, NavController, Platform } from '@ionic/angular';
import { RestService } from 'src/app/services/rest.service';
import { User } from 'src/app/model/user';
import { Role } from 'src/app/model/role';

@Component({
  selector: 'app-view-user',
  templateUrl: './view-user.page.html',
  styleUrls: ['./view-user.page.scss'],
})
export class ViewUserPage implements OnInit {
  validations_form: FormGroup;
  name: string;
  mail: string;
  secret: string;
  confirm: string;
  selection: number;
  Selection = {
    Login: 0,
    Register: 1
  }
  userSubmissions: UserSubmissionInformation[];
  HWBackSubscription: any;

  validation_messages = {
    'name': [
      { type: 'required', message: 'Name is required.' }
    ],
    'mail': [
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

  constructor(
    public formBuilder: FormBuilder,
    private navCtrl: NavController,
    private restService: RestService,
    public dataService: DataService,
    private alertCtrl: AlertController,
    private platform: Platform,
  ) {
    this.validations_form = this.formBuilder.group({
      name: ['', Validators.required],
      mail: ['', Validators.compose([
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
    this.selection = this.Selection.Login;
    this.userSubmissions = null;
  }

  ngOnInit(): void {
    this.getUserSubmissions();
  }

  ionViewDidEnter(){
    this.HWBackSubscription = this.platform.backButton.subscribe(() => {
      navigator['app'].exitApp();
    });
  }

  ionViewDidLeave(){
    this.HWBackSubscription.unsubscribe();
  }

  async reload(event?) {
    const newUser = await this.restService.getUser(this.dataService.getUser().mail);
    this.dataService.refreshUser(newUser);
    await this.getUserSubmissions();
    if (event)
      event.target.complete();
  }

  login() {
    let user: User = new User();
    user.mail = this.mail;
    user.secret = this.secret;
    this.restService.login(user).then(
      user => {
        this.restService.presentToast("Accesso eseguito come " + (user as unknown as User).name);
        this.secret = "";
        this.mail = "";
        this.validations_form.reset();
      }
    );
  }

  async register() {
    var newUser = new User();
    newUser.mail = this.mail;
    newUser.name = this.name;
    newUser.secret = this.secret;
    if (this.secret == this.confirm) {
      this.restService.createUser(newUser).then(
        res => {
          if (res) {
            this.restService.presentToast("Utente " + res.name + " creato con successo");
            this.secret = "";
            this.mail = "";
            this.name = "";
            this.confirm = "";
            this.validations_form.reset();
          }
        }
      );
    } else {
      this.restService.presentToast("Password and confirm password dont match.");
    }
  }

  async getUserSubmissions() {
    this.userSubmissions = await this.restService.getUserSubmissions();
  }

  async removeSubmission(userSubmission: UserSubmissionInformation, role: Role, slidingItem: any) {
    const index = this.userSubmissions.indexOf(userSubmission);
    this.userSubmissions.splice(index, 1);
    const res = await this.restService.rejectSubmission(userSubmission, role).catch(
      err => {
        this.userSubmissions.splice(index, 0, userSubmission);
      }
    );
    if (!res) {
      this.userSubmissions.splice(index, 0, userSubmission);
    }
    slidingItem.close();
  }

  async deleteUserSkill(skill: Skill, slidingItem: any) {
    const index = this.dataService.getUser().skills.indexOf(skill);
    this.dataService.getUser().skills.splice(index, 1);
    const res = await this.restService.removeSkill(skill).catch(
      err => {
        this.dataService.getUser().skills.splice(index, 0, skill);
      }
    );
    if (!res) {
      this.dataService.getUser().skills.splice(index, 0, skill);
    }
    slidingItem.close();
  }

  async addNewSkill() {
    const add = await this.alertCtrl.create({
      cssClass: 'my-custom-class',
      header: 'Add skill name !',
      message: 'skill  name required',
      inputs: [
        {
          name: 'skill',
          placeholder: 'skill'
        },
      ],
      buttons: [
        {
          text: 'cancel',
        }, {
          text: 'add',
          handler: async data => {
            if (data.skill == null || (data.skill as string).trim() == "") {
              this.restService.presentToast("Il campo skill non puo essere vuoto");
            } else {
              await this.restService.addNewSkill(data.skill)
                .then(
                  res => {
                    if (res) {
                      this.restService.getUser(this.dataService.getUser().mail)
                        .then(
                          user=>this.dataService.refreshUser(user)
                        );
                    }
                  }
                )
            }
          }
        }
      ]
    });
    await add.present();
  }
}
