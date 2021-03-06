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
  yourProjects: Project[];
  selection: string;
  yourLoading: boolean;
  allLoading: boolean;
  yourMessage: string;
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
    this.yourLoading = true;
    this.allLoading = true;
    this.allProjects = new Array();
    this.yourProjects = new Array();
    this.selection = "all";
    this.allMessage = "";
    this.yourMessage = "";
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
    if (this.selection == "all") {
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
    } else {
      this.restService.getUserProjects(this.dataService.getUser().mail)
        .then(res => {
          this.yourProjects = this.yourProjects.concat(res);
          if (this.yourProjects.length == 0)
            this.yourMessage = this.emptyMessage;
          else
            this.yourMessage = "";
          this.yourLoading = false;
        }).catch(err => {
          this.yourProjects = new Array();
          this.yourMessage = this.errorMessage;
          this.yourLoading = false;
        });
    }
  }

  async loadMore(event) {
    if (this.selection == "all") {
      this.page++;
      await this.loadProjects();
    }

    if (event) {
      event.target.complete();
    }
  }

  async reload(event?) {
    if (this.selection == "all") {
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
    } else {
      this.restService.getUserProjects(this.dataService.getUser().mail)
        .then(res => {
          this.yourProjects = res;
          if (event)
            event.target.complete();
          if (this.yourProjects.length == 0)
            this.yourMessage = this.emptyMessage;
          else
            this.yourMessage = "";
        }).catch(err => {
          this.yourProjects = new Array();
          this.yourMessage = this.errorMessage;
          if (event)
            event.target.complete();
        });
    }
  }

  viewProject(id: string) {
    this.navCtrl.navigateForward(['/tabs/list-of-projects/view-project', { "id": id }]);
  }

  createProject() {
    this.navCtrl.navigateForward(["/tabs/list-of-projects/create-project"]);
  }

  isProjectsEmpty(): boolean {
    if (this.selection == "all") {
      return this.allProjects.length == 0;
    } else {
      return this.yourProjects.length == 0;
    }
  }

  segmentChanged(event: any) {
    this.selection = event.detail.value;
    if (this.isLoading()) {
      this.loadProjects();
    }
  }

  getProjects() {
    if (this.selection == "all") {
      return this.allProjects;
    } else {
      return this.yourProjects;
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
