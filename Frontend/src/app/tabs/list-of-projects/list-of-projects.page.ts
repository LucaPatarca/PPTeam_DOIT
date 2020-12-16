import { Component } from '@angular/core';
import {HttpClient} from "@angular/common/http"
import { Title }     from '@angular/platform-browser';
import{Router} from "@angular/router";
import { MenuController } from '@ionic/angular';


@Component({
  selector: 'app-list-of-projects',
  templateUrl: './list-of-projects.page.html',
  styleUrls: ['./list-of-projects.page.scss'],
})
export class ListOfProjectsPage {
  projects = [];
  page = 0;
  maximumPages=9;

  constructor(
    private titleService: Title,
    private http: HttpClient,
    public router:Router,
    public menuCtrl:MenuController
    ) {
    this.loadProjects();
    this.titleService.setTitle("listOfProjects");
    this.menuCtrl.enable(true);
  }

  // metodo per richiedere una pagina di elementi
  loadProjects(event?){
    this.http.get(`http://localhost:8080/api/projects/list/${this.page}`)
    .subscribe(res => {
      console.log(res);
      this.projects= this.projects.concat(res['content']);
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
  viewProject(project:String){
    this.router.navigate(['/view-project',project]);
  }
}
