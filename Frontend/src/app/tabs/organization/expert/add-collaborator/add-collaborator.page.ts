import { NavController } from '@ionic/angular';
import { Component } from '@angular/core';
import { User } from 'src/app/model/user';
import { RestService } from 'src/app/services/rest.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-add-collaborator',
  templateUrl: './add-collaborator.page.html',
  styleUrls: ['./add-collaborator.page.scss'],
})
export class AddCollaboratorPage {

  users:User[];
  organizationId: string;

  constructor(
    private restService: RestService,
    private navCtrl:NavController,
    private route: ActivatedRoute,
  ) { 
    this.users = new Array();
    this.organizationId = this.route.snapshot.params["id"];
    this.loadUser();
  }

  async loadUser(event?){
    this.users = await this.restService.getOrganizationMembers(this.organizationId);
    if(event){
      event.target.complete();
    }
  }

  addSkill(user:User){
    this.navCtrl.navigateForward(["/add-skill",{"userMail":user.mail, "organizationId": this.organizationId}]);
  }
}
