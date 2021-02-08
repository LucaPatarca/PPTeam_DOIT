import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { MenuController, NavController } from '@ionic/angular';
import { DataService } from 'src/app/services/data.service';
import { GlobalsService } from 'src/app/services/globals.service';

@Component({
  selector: 'app-list-of-organizations',
  templateUrl: './list-of-organizations.page.html',
  styleUrls: ['./list-of-organizations.page.scss'],
})
export class ListOfOrganizationsPage {
  page = 0;
  textNoOrganizations = "Nessuna Organizzazione disponibile";

  constructor(
    private http: HttpClient,
    public data: DataService,
    public menuCtrl: MenuController,
    private navCtrl: NavController,
    private globals: GlobalsService
  ) {
    this.data.clearOrganizations();
    this.loadOrganizations();
  }

  ionViewWillEnter() {
    this.menuCtrl.enable(true);
  }

  // metodo per richiedere una pagina di elementi
  loadOrganizations(event?) {
    this.http.get(this.globals.listOfOrganizationsApiUrl + this.page)
      .subscribe(res => {
        this.data.addOrganization(res['content']);
        if (event) {
          event.target.complete();
        }
      });
  }

  loadMore(event: any) {
    this.page++;
    this.loadOrganizations(event);
  }


  // metodo per aprire la visualizzazione di una pagina (gli si passa un organization)
  viewOrganization(organizationId: String) {
    this.navCtrl.navigateForward(['/view-organization', { "id": organizationId }], { queryParams: { 'refresh': 1 } });
  }
}
