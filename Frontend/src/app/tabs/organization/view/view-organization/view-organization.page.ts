import { Organization } from './../../../../model/organization';
import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { MenuController, NavController, ActionSheetController } from '@ionic/angular';
import { DataService } from 'src/app/services/data.service';
import { RestService } from 'src/app/services/rest.service';

@Component({
  selector: 'app-view-organization',
  templateUrl: './view-organization.page.html',
  styleUrls: ['./view-organization.page.scss'],
})
export class ViewOrganizationPage {
  organization: Organization;
  id: string;

  constructor(
    private route: ActivatedRoute,
    private nav: NavController,
    public data: DataService,
    private menuCtrl: MenuController,
    private restService: RestService,
    private actionSheetCtrl: ActionSheetController,
  ) {
    this.id = this.route.snapshot.params["id"];
    this.organization = null;
  }

  ionViewDidEnter() {
    this.menuCtrl.enable(false);
    this.loadOrganization();
  }

  async loadOrganization() {
    this.organization = await this.restService.getOrganization(this.id);

  }

  async reload(event?) {
    await this.loadOrganization();
    event.target.complete();
  }

  goBack() {
    this.nav.navigateBack(["/list-of-organizations"], { queryParams: { 'refresh': 1 } });
  }

  async showActionSheet() {
    const actionSheet = await this.actionSheetCtrl.create({
      header: 'Organization',
      cssClass: 'my-custom-class',
      buttons: this.getButtons()
    });
    await actionSheet.present();
  }

  getButtons(): Array<Object> {
    var buttons = new Array();

    // azioni per i membri
    if (this.data.hasMemberPermission(this.organization)) {
      buttons = buttons.concat([
        {
          text: 'Edit',
          icon: 'create-outline',
          handler: () => {
            this.nav.navigateForward(['/modify-organization', { "id": this.organization.id }]);
          }
        }
      ]);
    }

    // azioni per il cratore
    if (this.data.hasCreatorPermission(this.organization)) {
      buttons = buttons.concat([
        {
          text: 'Delete',
          role: 'destructive',
          icon: 'trash',
          handler: () => {
            this.restService.deleteOrganization(this.organization);
            this.goBack();
          }
        }, {
          text: 'Add Expert',
          icon: 'person-add-outline',
          handler: () => {
            this.nav.navigateForward(["/add-expert", { "id": this.organization.id }]);
          }
        }, {
          text: 'Add Collaborator',
          icon: 'person-add-outline',
          handler: () => {
            this.nav.navigateForward(["/add-collaborator", { "id": this.organization.id }]);
          }
        }
      ]);
    }

    // azioni per tutti
    buttons = buttons.concat([
      {
        text: 'Cancel',
        icon: 'close',
        role: 'cancel',
        handler: () => { }
      }
    ]);

    return buttons;
  }

}


