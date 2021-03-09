import { NavController, ModalController } from '@ionic/angular';
import { Component, Input, OnInit } from '@angular/core';
import { Project } from 'src/app/model/project';

@Component({
  selector: 'app-your-projects',
  templateUrl: './your-projects.component.html',
  styleUrls: ['./your-projects.component.scss'],
})
export class YourProjectsComponent implements OnInit {

  @Input() projects: Project[];

  constructor(
    private navCtrl: NavController,
    private modalCtrl: ModalController
  ) { }

  ngOnInit() {
  }

  viewProject(projectId: String) {
    this.dismiss();
    this.navCtrl.navigateForward(['/tabs/home/view-project', { "id": projectId }]);
  }

  dismiss() {
    this.modalCtrl.dismiss({
      'dismissed': true
    });
  }

}
