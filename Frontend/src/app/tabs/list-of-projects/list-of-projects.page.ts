import { ViewProjectPage } from './../view-project/view-project.page';
import { Component } from '@angular/core';
import {HttpClient} from "@angular/common/http"
import { Title }     from '@angular/platform-browser';
import{Router} from "@angular/router";

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

    ) {
    this.loadProjects();
    this.titleService.setTitle("listOfProjects");
  }

  loadProjects(event?){
    this.http.get(`http://localhost:8080/api/project/list/${this.page}`)
    .subscribe(res => {
      console.log(res);
      this.projects= this.projects.concat(res['content']);
      if(event){
        event.target.complete();
      }
    });
  }

  loadMore(event){
      console.log(event);
        this.page++;
        this.loadProjects(event);

        if(this.page == this.maximumPages){
          event.target.disabled =true;
        }
  }

  prova(project:String){
    this.router.navigate(['/view-project',project]);
  }
}
