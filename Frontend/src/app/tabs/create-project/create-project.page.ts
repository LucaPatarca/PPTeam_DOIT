import { HttpClient,HttpHeaders } from '@angular/common/http';
import { Component } from '@angular/core';
import { MenuController } from '@ionic/angular';



@Component({
  selector: 'app-create-project',
  templateUrl: './create-project.page.html',
  styleUrls: ['./create-project.page.scss'],
})
export class CreateProjectPage {

  title_project:String;
  description_project:String;

  user = { "mail":"mail_user","name":"name_user","age":21}

  organization = {"name":"nome_org","description":"description_org","creatorMail":this.user.mail}



  constructor(private menuCtrl:MenuController,private http:HttpClient) { 
    this.menuCtrl.enable(false);
  }

  createProject(){
    // metodo per effettuare una chiamata post
    var newProject = {"name":this.title_project,"description":this.description_project,"organizationName":this.organization.name,"creatorMail":this.organization.creatorMail,"neededSkills":[],"closed":false,"team":{"roles":[],"projectName":this.title_project},"candidates":[]};
    console.log(newProject);
    this.http.post("http://localhost:8080/api/projects/createNew",newProject,{ headers: new HttpHeaders(), responseType: 'json'}).subscribe(
      res => {
        console.log('res2', res);	
      }, 
      err => { 
        console.log('oops some error in Project'); 
      }
    );
  }
}
