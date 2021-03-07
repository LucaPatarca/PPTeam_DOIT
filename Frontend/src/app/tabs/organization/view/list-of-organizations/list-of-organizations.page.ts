import { Organization } from './../../../../model/organization';
import { DataService } from 'src/app/services/data.service';
import { Component } from '@angular/core';
import { NavController, Platform } from '@ionic/angular';
import { OrganizationInformation } from 'src/app/model/organization-information';
import { RestService } from 'src/app/services/rest.service';

@Component({
  selector: 'app-list-of-organizations',
  templateUrl: './list-of-organizations.page.html',
  styleUrls: ['./list-of-organizations.page.scss'],
})
export class ListOfOrganizationsPage {
  page = 0;
  allOrganizations: Array<OrganizationInformation>;
  yourOrganizations: Array<Organization>;
  selection: string;
  yourLoading: boolean;
  allLoading: boolean;
  HWBackSubscription: any;
  allMessage: string;
  yourMessage: string;
  private readonly errorMessage = "Impossibile caricare le organizzazioni";
  private readonly emptyMessage = "Nessuna organizzazione disponibile";

  constructor(
    private navCtrl: NavController,
    private restService: RestService,
    private platform: Platform,
    public dataService: DataService
  ) {
    this.allOrganizations = new Array();
    this.yourOrganizations = new Array();
    this.yourLoading = true;
    this.allLoading = true;
    this.selection = 'all';
    this.allMessage = "";
    this.yourMessage = "";
    this.loadOrganizations();
  }

  ionViewDidEnter() {
    this.HWBackSubscription = this.platform.backButton.subscribe(() => {
      navigator['app'].exitApp();
    });
    this.reloadOrganizations();
  }

  ionViewDidLeave() {
    this.HWBackSubscription.unsubscribe();
  }

  // metodo per richiedere una pagina di elementi
  async loadOrganizations(event?) {
    if (this.selection == "all") {
      this.restService.getOrganizationPage(this.page)
        .then(res => {
          this.allOrganizations = this.allOrganizations.concat(res);
          if (event)
            event.target.complete();
          if (this.allOrganizations.length == 0)
            this.allMessage = this.emptyMessage;
          else
            this.allMessage = "";
          this.allLoading = false;
        }).catch(err => {
          this.allOrganizations = new Array();
          this.allMessage = this.errorMessage;
          if (event)
            event.target.complete();
          this.allLoading = false;
        });
    } else {
      this.restService.getUserOrganizations(this.dataService.getUser().mail)
        .then(res => {
          this.yourOrganizations = this.yourOrganizations.concat(res);
          if (event)
            event.target.complete();
          if (this.yourOrganizations.length == 0)
            this.yourMessage = this.emptyMessage;
          else
            this.yourMessage = "";
          this.yourLoading = false;
        }).catch(err => {
          this.yourOrganizations = new Array();
          this.yourMessage = this.errorMessage;
          if (event)
            event.target.complete();
          this.yourLoading = false;
        });
    }
  }

  reloadOrganizations(event?) {
    if (this.selection == "all") {
      this.page = 0;
      this.restService.getOrganizationPage(this.page)
        .then(res => {
          this.allOrganizations = res;
          if (event)
            event.target.complete();
          if (this.allOrganizations.length == 0)
            this.allMessage = this.emptyMessage;
          else
            this.allMessage = "";
        }).catch(err => {
          this.allOrganizations = new Array();
          this.allMessage = this.errorMessage;
          if (event)
            event.target.complete();
        });
    } else {
      this.restService.getUserOrganizations(this.dataService.getUser().mail)
        .then(res => {
          this.yourOrganizations = res;
          if (event)
            event.target.complete();
          if (this.yourOrganizations.length == 0)
            this.yourMessage = this.emptyMessage
          else
            this.yourMessage = "";
        }).catch(err => {
          this.yourOrganizations = new Array();
          this.yourMessage = this.errorMessage;
          if (event)
            event.target.complete();
        });
    }
  }

  loadMore(event: any) {
    if (this.selection == "all") {
      this.page++;
      this.loadOrganizations(event);
    }
  }


  // metodo per aprire la visualizzazione di una pagina (gli si passa un organization)
  viewOrganization(organizationId: String) {
    this.navCtrl.navigateForward(['/tabs/list-of-organizations/view-organization', { "id": organizationId }]);
  }

  createOrganization() {
    this.dataService.modify = null;
    this.navCtrl.navigateForward(["/tabs/list-of-organizations/create-organization"]);
  }

  segmentChanged(event: any) {
    this.selection = event.detail.value;
    if (this.isLoading()) {
      this.loadOrganizations();
    }
  }

  isOrganizationsEmpty(): boolean {
    if (this.selection == "all") {
      return this.allOrganizations.length == 0;
    } else {
      return this.yourOrganizations.length == 0;
    }
  }

  getOrganizations() {
    if (this.selection == "all") {
      return this.allOrganizations;
    } else {
      return this.yourOrganizations;
    }
  }

  getMessage() {
    if (this.selection == "all") {
      return this.allMessage;
    } else {
      return this.yourMessage;
    }
  }

  isLoading() {
    if (this.selection == "all") {
      return this.allLoading;
    } else {
      return this.yourLoading;
    }
  }
}
