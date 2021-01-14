import { ActivatedRoute } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { MenuController, AlertController, NavController } from '@ionic/angular';
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
    public nav: NavController, 
    private http:HttpClient,
    public data:DataService,
    private globals: GlobalsService,
    private alertCtrl:AlertController
  ) { 
    const id = this.route.snapshot.params["id"];
    this.project = this.data.getProject(id);
    if(this.project.closed){
      this.isClosed="closed";
    }else{
      this.isClosed="opened";
    }
    this.menuCtrl.enable(false);
  }

  onBack(){
    this.menuCtrl.enable(true);
    this.nav.navigateBack(["/list-of-projects"], { queryParams: { 'refresh': 1 } })
  }

  modify(){
    this.nav.navigateForward(['/modify-project', {"id":this.project.id}]);
  }

  delete(){
    this.http.delete(this.globals.projectApiUrl + this.project.id)
    .subscribe(
      async res => {
        console.log('Delete successful Project with Id: ' + this.project.id);
        this.data.removeProject(this.project);	
        const alert = await this.alertCtrl.create({
          cssClass: 'my-custom-class',
          header: 'Cancellato',
          message: 'Progetto Cancellato.',
          buttons: ['OK']
        });
      
        await alert.present();
      }, 
      err => { 
        console.log('There was an error!', err); 
      }
    );
    this.onBack();
  }

  public reload(event?){
    this.http.get(this.globals.projectApiUrl+this.project.id).subscribe(
      res=>{
        console.log(res);
        if(res != null){
          const reloadedProject: Project = res as Project;
          this.data.updateProject(this.project.id,reloadedProject);
          this.project = reloadedProject;
          if(this.project.closed){
            this.isClosed="closed";
          }else{
            this.isClosed="opened";
          }
        }
      },
      err=>{
        console.log(err);
      }
    );
    event.target.complete();
  }
  

}


