import { Component } from '@angular/core';
import { Router, ActivatedRoute } from "@angular/router";
import { HttpClient,HttpHeaders } from '@angular/common/http';
import { MenuController } from '@ionic/angular';
import { Project } from 'src/app/model/project';
import { DataService } from 'src/app/services/data.service';
import { GlobalsService } from 'src/app/services/globals.service';

@Component({
  selector: 'app-modify-project',
  templateUrl: './modify-project.page.html',
  styleUrls: ['./modify-project.page.scss'],
})
export class ModifyProjectPage {
  project:Project;

  constructor(
    private route:ActivatedRoute, 
    public router:Router, 
    private menuCtrl:MenuController,
    private http:HttpClient,
    private dataService:DataService,
    private globals:GlobalsService
  ) { 
    const id = this.route.snapshot.params['id'];
    this.project = dataService.getProject(id);
    this.menuCtrl.enable(false);
  }

  
    

  save(){
    this.http.put(this.globals.modifyProjectApiUrl, this.project, { headers: new HttpHeaders(), responseType: 'json'})
    .subscribe(
      res => {
        console.log('Successfully saved Project with Id: ' + this.project.id);	
        this.dataService.updateProject(this.project.id,res as Project);
        this.viewProject(res['id']);
      }, 
      err => { 
        console.log('There was an error!', err); 
      }
    );
  }

  viewProject(id:string){
    this.router.navigate(['/view-project',{'id':id}]);
  }

}
