import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { AlertController, MenuController, NavController } from '@ionic/angular';
import { Skill } from 'src/app/model/skill';
import { User } from 'src/app/model/user';
import { DataService } from 'src/app/services/data.service';
import { GlobalsService } from 'src/app/services/globals.service';
import { RestService } from 'src/app/services/rest.service';

@Component({
  selector: 'app-view-skill',
  templateUrl: './view-skill.page.html',
  styleUrls: ['./view-skill.page.scss'],
})
export class ViewSkillPage {

  skills: Skill[];
  page = 0;
  textNoSkill = "Nessuna skill disponibile";
  loading: boolean;

  constructor(
    public data: DataService,
    public menuCtrl: MenuController,
    private restService: RestService,
  ) {
    this.loading = true;
    this.skills = new Array()
    this.loadSkills().then(
      ()=>{
        this.loading = false;
      }
    );
  }

  // metodo per richiedere una pagina di elementi
  async loadSkills(event?) {
    const newSkills = await  this.restService.getUserSkills((this.data.getUser() as unknown as User).mail);
    this.skills = this.skills.concat(newSkills);
  }

  async loadMore(event: any) {
    if(!this.loading){
      this.page++;
      await this.loadSkills(event);
      if (event) {
        event.target.complete();
      }
    }
  }

  async reload(event?){
    this.page=0;
    const newSkills = await this.restService.getUserSkills((this.data.getUser() as unknown as User).mail);
    this.skills = newSkills;
    event.target.complete();
  }

}
