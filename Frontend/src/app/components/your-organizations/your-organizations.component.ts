import { Organization } from './../../model/organization';
import { Component, Input, OnInit } from '@angular/core';
import { ModalController, NavController } from '@ionic/angular';

@Component({
  selector: 'app-your-organizations',
  templateUrl: './your-organizations.component.html',
  styleUrls: ['./your-organizations.component.scss'],
})
export class YourOrganizationsComponent implements OnInit {
  
  @Input() organizations: Organization[];

  constructor(
    private navCtrl: NavController,
    private modalCtrl: ModalController
  ) { }

  ngOnInit() {
  }

  viewOrganziation(orgId: String) {
    this.dismiss();
    this.navCtrl.navigateForward(['/tabs/home/view-organization', { "id": orgId }]);
  }

  dismiss() {
    this.modalCtrl.dismiss({
      'dismissed': true
    });
  }
}
