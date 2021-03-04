import { DataService } from 'src/app/services/data.service';
import { Component } from '@angular/core';
import { MenuController, NavController, Platform } from '@ionic/angular';
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
  loading: boolean;
  HWBackSubscription: any;

  constructor(
    public menuCtrl: MenuController,
    private navCtrl: NavController,
    private restService: RestService,
    private platform: Platform,
    public dataService: DataService
  ) {
    this.loading = true;
    this.allProjects = new Array();
    this.yourProjects = new Array();
    this.selection = "all";
    this.loadProjects().then(() => {
      this.loading = false;
    });
  }

  ionViewDidEnter() {
    this.menuCtrl.enable(true);
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
      const newProjects = await this.restService.getProjectsPage(this.page);
      this.allProjects = this.allProjects.concat(newProjects);
    } else {
      this.yourProjects = await this.restService.getUserProjects(this.dataService.getUser().mail);
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
      const newProjects = await this.restService.getProjectsPage(this.page);
      this.allProjects = newProjects;
    } else {
      this.yourProjects = await this.restService.getUserProjects(this.dataService.getUser().mail);
    }
    if (event)
      event.target.complete();
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
    if (this.isProjectsEmpty()) {
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
}
