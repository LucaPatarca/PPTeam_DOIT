import { Router } from '@angular/router';
import { HttpClient,HttpHeaders } from '@angular/common/http';
import { Component } from '@angular/core';
import { MenuController } from '@ionic/angular';
import { GlobalsService } from 'src/app/services/globals.service';



@Component({
  selector: 'app-create-project',
  templateUrl: './create-project.page.html',
  styleUrls: ['./create-project.page.scss'],
})
export class CreateProjectPage {

  title_project:string;
  description_project:string;

  constructor(private menuCtrl:MenuController,
    private http:HttpClient,
    private router:Router,
    private globals: GlobalsService) { 
    this.menuCtrl.enable(false);
  }

  createProject(){
    // metodo per effettuare una chiamata post
    var newProject = {"name":this.title_project,"description":this.description_project,"organizationName":this.globals.defaultOrganizationName,"creatorMail":this.globals.userMail,"neededSkills":[],"closed":false,"team":{"roles":[],"projectName":this.title_project},"candidates":[]};
    this.http.post(this.globals.createProjectApiUrl,newProject,{ headers: new HttpHeaders(), responseType: 'json'}).subscribe(
      res => {
        console.log('Successfully created new project');
        this.menuCtrl.enable(true);
        this.router.navigate(['/list-of-projects']);
      }, 
      err => { 
        console.log('oops some error in Project'); 
      }
    );
  }

  back(){
    this.menuCtrl.enable(true);
    this.router.navigate(['/home'], { queryParams: { 'refresh': 1 } });
  }
}
