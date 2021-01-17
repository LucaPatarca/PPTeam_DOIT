import { Component } from '@angular/core';
import {HttpClient} from "@angular/common/http"
import { Title }     from '@angular/platform-browser';
import { Router } from "@angular/router";
import { MenuController,NavController } from '@ionic/angular';
import { DataService } from 'src/app/services/data.service';
import { GlobalsService } from 'src/app/services/globals.service';
import { Project } from 'src/app/model/project';


@Component({
  selector: 'app-list-of-projects',
  templateUrl: './list-of-projects.page.html',
  styleUrls: ['./list-of-projects.page.scss'],
})
export class ListOfProjectsPage {
  page = 0;

  constructor(
    private titleService: Title,
    private http: HttpClient,
    public data: DataService,
    public router:Router,
    public menuCtrl:MenuController,
    private navCtrl:NavController,
    private globals:GlobalsService
    ) {
    this.data.clearProject();
    this.loadProjects();
    this.titleService.setTitle("listOfProjects");
    this.menuCtrl.enable(true);
    }

  // metodo per richiedere una pagina di elementi
  loadProjects(event?){
    this.http.get(this.globals.listOfProjectsApiUrl+this.page)
    .subscribe(res => {
      console.log(res);
      const toAdd:Project[] = res['content'] as Project[];
      toAdd.forEach(project=>this.data.addProject(project));
      if(event){
        event.target.complete();
      }
    });
  }

  loadMore(event){
        this.page++;
        this.loadProjects(event);
  }


  // metodo per aprire la visualizzazione di una pagina (gli si passa un project)
  viewProject(id:string){
    this.navCtrl.navigateForward(['/view-project',{"id":id}]);
  }
}
