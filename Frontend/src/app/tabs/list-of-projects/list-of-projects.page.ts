import { Component } from '@angular/core';
import {HttpClient} from "@angular/common/http"
import { Title }     from '@angular/platform-browser';
import{Router} from "@angular/router";
import { MenuController } from '@ionic/angular';
import { DataService } from 'src/app/services/data.service';
import { Project } from 'src/app/model/project';


@Component({
  selector: 'app-list-of-projects',
  templateUrl: './list-of-projects.page.html',
  styleUrls: ['./list-of-projects.page.scss'],
})
export class ListOfProjectsPage {
  page = 0;
  textNoProjects="";  

  constructor(
    private titleService: Title,
    private http: HttpClient,
    public data: DataService,
    public router:Router,
    public menuCtrl:MenuController
    ) {
    this.data.clear();
    this.loadProjects();
    this.titleService.setTitle("listOfProjects");
    this.menuCtrl.enable(true);
  }

  // metodo per richiedere una pagina di elementi
  loadProjects(event?){
    this.http.get(`http://localhost:8080/api/projects/list/${this.page}`)
    .subscribe(res => {
      this.data.addProject(res['content']);
      if(this.data.list.length==0){
        this.textNoProjects = "nessun progetto disponibile";
      }else{
        this.textNoProjects = "";
      }
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
    this.router.navigate(['/view-project',{"id":id}]);
  }
}
