import { ActivatedRoute } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { MenuController } from '@ionic/angular';
import{Router} from "@angular/router";
import { HttpClient } from '@angular/common/http';
import { Project } from 'src/app/model/project';
import { DataService } from 'src/app/services/data.service';
import { GlobalsService } from 'src/app/services/globals.service';


@Component({
  selector: 'app-view-project',
  templateUrl: './view-project.page.html',
  styleUrls: ['./view-project.page.scss'],
})

export class ViewProjectPage  {
isClosed:string;
project:Project;
emptyNeededskills:String;
emptyCandidates:String;
  constructor(
    private route:ActivatedRoute, 
    private menuCtrl:MenuController, 
    public router:Router, 
    private http:HttpClient,
    public data:DataService,
    private globals: GlobalsService
  ) { 
    const id = this.route.snapshot.params["id"];
    this.project = this.data.getProject(id);
    if(this.project.closed){
      this.isClosed="open";
    }else{
      this.isClosed="closed";
    }
    this.menuCtrl.enable(false);
  }

  onClick(){
    this.menuCtrl.enable(true);
    this.router.navigate(["/list-of-projects"], { queryParams: { 'refresh': 1 } })
  }

  modify(){
    this.router.navigate(['/modify-project', {"id":this.project.id}]);
  }

  delete(){
    this.http.delete(this.globals.projectApiUrl + this.project.id)
    .subscribe(
      res => {
        console.log('Delete successful Project with Id: ' + this.project.id);
        this.data.removeProject(this.project);	
      }, 
      err => { 
        console.log('There was an error!', err); 
      }
    );
    this.onClick();
  }
  

}


