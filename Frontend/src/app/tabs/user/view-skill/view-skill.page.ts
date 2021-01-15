import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { AlertController, MenuController, NavController } from '@ionic/angular';
import { Skill } from 'src/app/model/skill';
import { DataService } from 'src/app/services/data.service';
import { GlobalsService } from 'src/app/services/globals.service';

@Component({
  selector: 'app-view-skill',
  templateUrl: './view-skill.page.html',
  styleUrls: ['./view-skill.page.scss'],
})
export class ViewSkillPage {

  listSkills:Skill[] = new Array();
  page = 0;
  textNoSkill="Nessuna skill disponibile";  

  constructor(
    private titleService: Title,
    private http: HttpClient,
    public data: DataService,
    public menuCtrl:MenuController,
    private navCtrl:NavController,
    private globals:GlobalsService,
    private alertCtrl:AlertController
    ) {
    this.loadSkill();
    this.titleService.setTitle("viewSkill");
  }

  // metodo per richiedere una pagina di elementi
  loadSkill(event?){
    this.http.get(this.globals.getUserSkills+this.data.userMail)
    .subscribe(res => {
      const toAdd:Skill[] = res as Skill[];
      toAdd.forEach(skill=>this.listSkills.push(skill));
      if(event){
        event.target.complete();
      }
    }, 
    err => { 
      console.log('oops some error in select org'); 
    });
  }

  loadMore(event: any){
        this.page++;
        this.loadSkill(event);
  }

  onBack(){
    this.menuCtrl.enable(true);
    this.navCtrl.navigateBack(["/home"], { queryParams: { 'refresh': 1 } })
  }

}
