import { MenuController, NavController, ToastController } from '@ionic/angular';
import { GlobalsService } from 'src/app/services/globals.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { DataService } from 'src/app/services/data.service';
import { Component } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-add-skill',
  templateUrl: './add-skill.page.html',
  styleUrls: ['./add-skill.page.scss'],
})
export class AddSkillPage {

  user: String;
  validations_form: FormGroup;
  skill: string;

  constructor(
    private route: ActivatedRoute,
    private formBuilder: FormBuilder,
    private dataService: DataService,
    private http: HttpClient,
    private globals: GlobalsService,
    private toastCtrl: ToastController,
    private navCtrl: NavController
  ) {
    this.user = this.route.snapshot.params["userMail"];
    this.validations_form = this.formBuilder.group({
      skill: ['', Validators.required],
    });
  }

  validation_messages = {
    'skill': [
      { type: 'required', message: 'name is required.' },
    ]
  };

  addSkill() {
    const newSkill = {
      "name": this.skill,
      "expertInOrganization": [this.dataService.selectedOrganization.id],
      "isGloballyExpert": false
    }

    this.http.put(this.globals.userAddSkillCollaborator + this.dataService.selectedOrganization + "/" + this.user + "/" + newSkill.name, { headers: new HttpHeaders(), responseType: 'json' }).subscribe(
      async res => {
        const toast = await this.toastCtrl.create({
          message: "Skill Aggiunta",
          duration: 2000
        });
        toast.present();

        this.navCtrl.navigateRoot(["/list-of-organizations"], { queryParams: { 'refresh': 1 } });
      },
      async err => {
        const toast = await this.toastCtrl.create({
          message: "Skill Aggiunta",
          duration: 2000
        });
        toast.present();
      }
    );
  }

}