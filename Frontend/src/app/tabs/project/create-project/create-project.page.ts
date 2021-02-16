import { Organization } from './../../../model/organization';
import { Component } from '@angular/core';
import { MenuController, NavController } from '@ionic/angular';
import { DataService } from 'src/app/services/data.service';
import { Validators, FormBuilder, FormGroup } from '@angular/forms';
import { Project } from 'src/app/model/project';
import { RestService } from 'src/app/services/rest.service';
import { User } from 'src/app/model/user';



@Component({
  selector: 'app-create-project',
  templateUrl: './create-project.page.html',
  styleUrls: ['./create-project.page.scss'],
})
export class CreateProjectPage {

  validations_form: FormGroup;
  title: string;
  description: string;
  validation_messages = {
    'title': [
      { type: 'required', message: 'Name is required.' }
    ],
    'description': [
      { type: 'required', message: 'Email is required.' },
      { type: 'pattern', message: 'Please enter a valid email.' }
    ],
  };

  constructor(private menuCtrl: MenuController,
    public formBuilder: FormBuilder,
    private navCtrl: NavController,
    public dataService: DataService,
    private restService: RestService,
    ) {
    this.validations_form = this.formBuilder.group({
      title: ['', Validators.required],
      description: ['', Validators.required],
    });
  }

  ionViewDidEnter() {
    this.menuCtrl.enable(false);
  }

  createProject() {
    // metodo per effettuare una chiamata post
    const project = new Project(
      this.title,
      this.description,
      (this.dataService.getOrganization() as unknown as Organization).id,
      (this.dataService.getUser() as unknown as User).mail,
    );
    
    this.restService.createProject(project).then(
      value=>{
        this.goToProjectsList();
      }
    );
  }

  goToProjectsList() {
    this.navCtrl.navigateRoot(['/list-of-projects'], { queryParams: { 'refresh': 1 } });
  }
}
