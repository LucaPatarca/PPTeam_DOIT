import { DataService } from 'src/app/services/data.service';
import { Component } from '@angular/core';
import { NavController, Platform } from '@ionic/angular';
import { RestService } from 'src/app/services/rest.service';
import { ProjectInformation } from 'src/app/model/project-information';
import { Project } from 'src/app/model/project';


@Component({
  selector: 'app-list-of-projects',
  templateUrl: './list-of-projects.page.html',
  styleUrls: ['./list-of-projects.page.scss'],
})
export class ListOfProjectsPage {
  page = 0;
  allProjects: ProjectInformation[];
  allLoading: boolean;
  allMessage: string;
  HWBackSubscription: any;
  private readonly emptyMessage = "Nessun progetto disponibile";
  private readonly errorMessage = "Impossibile caricare i progetti";

  constructor(
    private navCtrl: NavController,
    private restService: RestService,
    private platform: Platform,
    public dataService: DataService
  ) {
    this.allLoading = true;
    this.allProjects = new Array();
    this.allMessage = "";
    this.loadProjects();
  }

  ionViewDidEnter() {
    this.HWBackSubscription = this.platform.backButton.subscribe(() => {
      navigator['app'].exitApp();
    });
    this.reload();
  }

  ionViewDidLeave() {
    this.HWBackSubscription.unsubscribe();
  }

  // metodo per richiedere una pagina di elementi
  async loadProjects() {
    this.restService.getProjectsPage(this.page)
      .then(res => {
        this.allProjects = this.allProjects.concat(res);
        if (this.allProjects.length == 0)
          this.allMessage = this.emptyMessage;
        else
          this.allMessage = "";
        this.allLoading = false;
      }).catch(err => {
        this.allProjects = new Array();
        this.allMessage = this.errorMessage;
        this.allLoading = false;
      });
  }

  async loadMore(event) {
    this.page++;
    await this.loadProjects();
    if (event) {
      event.target.complete();
    }
  }

  async reload(event?) {
    this.page = 0;
    this.restService.getProjectsPage(this.page)
      .then(res => {
        this.allProjects = res;
        if (event)
          event.target.complete();
        if (this.allProjects.length == 0)
          this.allMessage = this.emptyMessage;
        else
          this.allMessage = "";
      }).catch(err => {
        this.allProjects = new Array();
        this.allMessage = this.errorMessage;
        if (event)
          event.target.complete();
      });
  }

  viewProject(id: string) {
    this.navCtrl.navigateForward(['/tabs/list-of-projects/view-project', { "id": id }]);
  }

  createProject() {
    this.dataService.modify = null;
    this.navCtrl.navigateForward(["/tabs/list-of-projects/create-project"]);
  }

  isProjectsEmpty(): boolean {
      return this.allProjects.length == 0;
  }

  segmentChanged(event: any) {
    if (this.isLoading()) {
      this.loadProjects();
    }
  }

  getProjects() {
    return this.allProjects;
  }

  getMessage() {
    return this.allMessage;
  }

  isLoading() {
    return this.allLoading;
  }
}
