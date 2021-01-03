import { Component } from '@angular/core';
import { Router, ActivatedRoute } from "@angular/router";
import { HttpClient,HttpHeaders } from '@angular/common/http';
import { MenuController } from '@ionic/angular';

@Component({
  selector: 'app-modify-project',
  templateUrl: './modify-project.page.html',
  styleUrls: ['./modify-project.page.scss'],
})
export class ModifyProjectPage {
  data:any;

  name:String;
  description:String;

  constructor(
    private route:ActivatedRoute ,  public router:Router, private menuCtrl:MenuController,private http:HttpClient
  ) { 
    this.data = this.route.snapshot.params;
    this.menuCtrl.enable(false);
  }

  
    

  save(){

    var modifyProject = {"name":this.name,"description":this.description,"organizationName":this.data.organizationName,"creatorMail":this.data.creatorMail, "neededSkills":this.data.neededSkills,"closed":this.data.closed,"team":this.data.team,"candidates":this.data.candidates};
    this.http.put("http://localhost:8080/api/projects/modify", modifyProject, { headers: new HttpHeaders(), responseType: 'json'})
    .subscribe(
      res => {
        console.log('Close successful Project with Id: ' + this.data.organizationName + '.' + this.data.name, res);	
      }, 
      err => { 
        console.log('There was an error!', err); 
      }
    );
    this.menuCtrl.enable(true);
  }

  viewProject(){
    this.router.navigate(['/view-project',this.data]);
    this.menuCtrl.enable(true);
  }

}
