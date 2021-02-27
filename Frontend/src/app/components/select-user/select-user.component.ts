import { LocationStrategy } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { AlertController, ModalController } from '@ionic/angular';
import { Page } from 'src/app/model/page';
import { Skill } from 'src/app/model/skill';
import { User } from 'src/app/model/user';
import { RestService } from 'src/app/services/rest.service';

@Component({
  selector: 'app-select-user',
  templateUrl: './select-user.component.html',
  styleUrls: ['./select-user.component.scss'],
})
export class SelectUserComponent implements OnInit {

  private users: User[];
  private page: number;
  private selected: string;
  private search: string;
  private last: boolean;

  constructor(
    private modalCtrl: ModalController,
    private restService: RestService
  ) { }

  ngOnInit() {
    this.page = 0;
    this.users = new Array();
    this.selected = "all";
    this.search = "";
    this.load();
  }

  async load() {
    const usersPage = await this.restService.getUserPage(this.page);
    this.users = this.users.concat(usersPage.content);
    this.last = usersPage.last;
  }

  dismiss() {
    this.modalCtrl.dismiss({
      'dismissed': true
    });
  }

  async segmentChanged(event?) {
    this.selected = event.detail.value;
    while(this.getUsers().length<10 && !this.last){
      await this.loadMore();
    }
  }

  isUsersEmpty(): boolean {
    return this.getUsers().length == 0;
  }

  getUsers(): User[] {
    if (this.selected == "all")
      return this.users.filter(u=>u.mail.includes(this.search));
    else if (this.selected == "experts") {
      return this.users.filter(it => it.skills.find(skill => skill.level >= 10)).filter(u=>u.mail.includes(this.search));
    }
  }

  async selectUser(user: User) {
    this.modalCtrl.dismiss({
      'user': user
    });
  }

  getExpertSkills(user: User): Skill[]{
    return user.skills.filter(it=>it.level>=10);
  }

  async inputChanged(event){
    const newValue = event.target.value;
    this.search=newValue;
    while(this.getUsers().length<10 && !this.last){
      await this.loadMore();
    }
  }

  async loadMore(event?) {
    this.page++;
    await this.load();
    if (event){
      event.target.complete();
    }
  }

}
