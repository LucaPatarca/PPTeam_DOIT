import { Component } from '@angular/core';
import { MenuController, NavController } from '@ionic/angular';
import { RestService } from 'src/app/services/rest.service';
import { ProjectInformation } from 'src/app/model/project-information';


@Component({
  selector: 'app-list-of-projects',
  templateUrl: './list-of-projects.page.html',
  styleUrls: ['./list-of-projects.page.scss'],
})
export class ListOfProjectsPage {
  page = 0;
  projects: ProjectInformation[];
  loading: boolean;

  constructor(
    public menuCtrl: MenuController,
    private navCtrl: NavController,
    private restService: RestService,
  ) {
    this.loading = true;
    this.projects = new Array();
    this.loadProjects().then(()=>{
      this.loading = false;
    });
  }

  ionViewDidEnter() {
    this.menuCtrl.enable(true);
  }

  // metodo per richiedere una pagina di elementi
  async loadProjects() {
    const newProjects = await this.restService.getProjectsPage(this.page);
    this.projects = this.projects.concat(newProjects);
  }

  async loadMore(event) {
    this.page++;
    await this.loadProjects();
    if (event) {
      event.target.complete();
    }
  }

  async reload(event){
    this.page = 0;
    const newProjects = await this.restService.getProjectsPage(this.page);
    this.projects = newProjects;
    event.target.complete();
  }

  viewProject(id: string) {
    this.navCtrl.navigateForward(['/view-project', { "id": id }]);
  }
}
