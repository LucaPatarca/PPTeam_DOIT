import { Component } from '@angular/core';
import { ActivatedRoute } from "@angular/router";
import { MenuController, NavController } from '@ionic/angular';
import { Project } from 'src/app/model/project';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { RestService } from 'src/app/services/rest.service';

@Component({
  selector: 'app-modify-project',
  templateUrl: './modify-project.page.html',
  styleUrls: ['./modify-project.page.scss'],
})
export class ModifyProjectPage {
  project: Project;
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

  constructor(
    private route: ActivatedRoute,
    public nav: NavController,
    private menuCtrl: MenuController,
    public formBuilder: FormBuilder,
    private restService: RestService,
  ) {
    this.validations_form = this.formBuilder.group({

      title: ['', Validators.required],
      description: [Validators.required],
    });
    const id = this.route.snapshot.params['id'];
    this.project = new Project("Title", "Description", "", "",new Array());
    restService.getProject(id).then(
      project=>{
        this.project = project;
      }
    )
  }

  ionViewDidlEnter() {
    this.menuCtrl.enable(false);
  }

  save() {
    this.project.title = this.title;
    this.project.description = this.description;
    this.restService.updateProject(this.project);
    this.goBack(this.project.id);
  }

  public goBack(id: string) {
    this.nav.navigateBack(['/view-project', { 'id': id }]);
  }

}
