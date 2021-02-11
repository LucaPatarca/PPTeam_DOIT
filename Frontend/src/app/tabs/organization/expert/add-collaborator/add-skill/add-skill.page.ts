import { MenuController, NavController, ToastController } from '@ionic/angular';
import { GlobalsService } from 'src/app/services/globals.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { DataService } from 'src/app/services/data.service';
import { Component } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RestService } from 'src/app/services/rest.service';

@Component({
  selector: 'app-add-skill',
  templateUrl: './add-skill.page.html',
  styleUrls: ['./add-skill.page.scss'],
})
export class AddSkillPage {

  userMail: string;
  organizationId: string;
  validations_form: FormGroup;
  skill: string;

  constructor(
    private route: ActivatedRoute,
    private formBuilder: FormBuilder,
    private dataService: DataService,
    private restService: RestService,
    private navCtrl: NavController,
  ) {
    this.userMail = this.route.snapshot.params["userMail"];
    this.organizationId = this.route.snapshot.params["organizationId"];
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
      "expertInOrganization": [this.organizationId],
      "isGloballyExpert": false
    }

    this.restService.addCollaborator(this.organizationId,this.userMail,newSkill);
    this.navCtrl.navigateRoot(["/list-of-organizations"], { queryParams: { 'refresh': 1 } });
  }

}