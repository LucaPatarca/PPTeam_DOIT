import { ActivatedRoute } from '@angular/router';
import { Component } from '@angular/core';
import { MenuController } from '@ionic/angular';
import{Router} from "@angular/router";
import { HttpClient } from '@angular/common/http';
import { Project } from 'src/app/model/project';
import { DataService } from 'src/app/services/data.service';


@Component({
  selector: 'app-view-project',
  templateUrl: './view-project.page.html',
  styleUrls: ['./view-project.page.scss'],
})

export class ViewProjectPage  {
isClosed:string;
project:Project;
  constructor(
    private route:ActivatedRoute, 
    private menuCtrl:MenuController, 
    public router:Router, 
    private http:HttpClient,
    public data:DataService
  ) { 
    const id = this.route.snapshot.params["id"];
    this.project = this.data.getProject(id);
    if(this.project.closed){
      this.isClosed="true";
    }else{
      this.isClosed="false";
    }
    this.menuCtrl.enable(false);
  }

  onClick(){
    this.menuCtrl.enable(true);
  }

  modify(){
    this.router.navigate(['/modify-project', {"id":this.project.id}]);
  }

  delete(){
    this.http.delete("http://localhost:8080/api/projects/delete/" + this.project.id)
    .subscribe(
      res => {
        console.log('Delete successful Project with Id: ' + this.project.id);
        this.data.removeProject(this.project);	
      }, 
      err => { 
        console.log('There was an error!', err); 
      }
    );

    this.router.navigate(['/list-of-projects']);
    this.onClick();
  }

}


