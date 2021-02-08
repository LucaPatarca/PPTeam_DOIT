import { NavController } from '@ionic/angular';
import { DataService } from 'src/app/services/data.service';
import { GlobalsService } from './../../../../services/globals.service';
import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { User } from 'src/app/model/user';

@Component({
  selector: 'app-add-collaborator',
  templateUrl: './add-collaborator.page.html',
  styleUrls: ['./add-collaborator.page.scss'],
})
export class AddCollaboratorPage {

  users:User[];

  constructor(
    private http:HttpClient,
    private globals:GlobalsService,
    private data:DataService,
    private navCtrl:NavController
  ) { 
    this.users = new Array();
    this.loadUser();
  }

  loadUser(event?){
    this.http.get(this.globals.getOrganizationMember+this.data.selectedOrganization)
    .subscribe(res => {
      const toAdd:User[] = res as User[];
      toAdd.forEach(user=>this.users.push(user));
      if(event){
        event.target.complete();
      }
    });
  }

  addSkill(user:User){
    this.navCtrl.navigateForward(["/add-skill",{"userMail":user.mail}]);
  }
}
