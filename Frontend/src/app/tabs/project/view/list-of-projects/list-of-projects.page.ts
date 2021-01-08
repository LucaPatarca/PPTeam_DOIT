import { Component } from '@angular/core';
import {HttpClient} from "@angular/common/http"
import { Title }     from '@angular/platform-browser';
import{ActivatedRouteSnapshot, DetachedRouteHandle, Router, RouteReuseStrategy} from "@angular/router";
import { MenuController } from '@ionic/angular';
import { DataService } from 'src/app/services/data.service';
import { GlobalsService } from 'src/app/services/globals.service';


@Component({
  selector: 'app-list-of-projects',
  templateUrl: './list-of-projects.page.html',
  styleUrls: ['./list-of-projects.page.scss'],
})
export class ListOfProjectsPage implements RouteReuseStrategy {
  page = 0;
  textNoProjects="";  

  constructor(
    private titleService: Title,
    private http: HttpClient,
    public data: DataService,
    public router:Router,
    public menuCtrl:MenuController,
    private globals:GlobalsService
    ) {
    this.data.clear();
    console.log("bella");
    this.loadProjects();
    this.titleService.setTitle("listOfProjects");
    this.menuCtrl.enable(true);
    }
  shouldDetach(route: ActivatedRouteSnapshot): boolean {
    throw new Error('Method not implemented.');
  }
  store(route: ActivatedRouteSnapshot, handle: DetachedRouteHandle): void {
    throw new Error('Method not implemented.');
  }
  shouldAttach(route: ActivatedRouteSnapshot): boolean {
    throw new Error('Method not implemented.');
  }
  retrieve(route: ActivatedRouteSnapshot): DetachedRouteHandle {
    throw new Error('Method not implemented.');
  }
  shouldReuseRoute(future: ActivatedRouteSnapshot, curr: ActivatedRouteSnapshot): boolean {
    throw new Error('Method not implemented.');
  }

  // metodo per richiedere una pagina di elementi
  loadProjects(event?){
    this.http.get(this.globals.listOfProjectsApiUrl+this.page)
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
