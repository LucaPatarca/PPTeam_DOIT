import { Skill } from './../model/skill';
import { Organization } from '../model/organization';
import { Injectable } from '@angular/core';
import { User } from '../model/user';
import { Project } from '../model/project';
import { Storage } from '@ionic/storage';

@Injectable({
  providedIn: 'root'
})
export class DataService {

  private key_user: string = 'user';
  private key_token: string = 'token';
  private key_darkMode: string = 'darkMode';

  private guestUser: User = new User();

  private user: User;
  private token: string;

  private darkModeEnabled: boolean;

  public isInternetConnected: boolean;

  public modify:any = null;

  private whenUserLoaded: Function[];

  constructor(private storage: Storage) {
    this.user = this.guestUser;
    this.token = "";
    this.darkModeEnabled = false;
    this.whenUserLoaded = new Array();

    storage.get(this.key_token).then((val) => {
      if (val != null)
        this.token = val as string;
    });
    storage.get(this.key_user).then((val) => {
      if (val != null) {
        this.user = val as User;
        this.whenUserLoaded.forEach(it=>it());
      }
    });
    storage.get(this.key_darkMode).then(val => {
      if (val != null) {
        this.darkModeEnabled = val;
        document.body.classList.toggle('dark', this.darkModeEnabled);
      } else {
        document.body.classList.toggle('dark', false);
      }
    });
  }

  public loginUser(user: User, token: string) {
    this.refreshUser(user);
    this.token = token;
    this.storage.set(this.key_token, token);
  }

  public getUser(): User {
    return this.user;
  }

  public refreshUser(user: User) {
    this.user = user;
    this.storage.set(this.key_user, user);
  }

  public refreshToken(token: string){
    this.token = token;
    this.storage.set(this.key_token, token);
  }

  public getUserMail(): string {
    return this.user.mail;
  }

  public isUserLogged(): boolean {
    return this.user != this.guestUser && this.token != "";
  }

  public logoutUser() {
    this.storage.remove(this.key_user);
    this.storage.remove(this.key_token);
    this.user = this.guestUser;
    this.token = "";
  }

  public hasProjectCreatorPermission(project: Project): boolean {
    if(!project) return false;
    if (this.user.mail == project.creatorMail)
      return true;
    else
      return false;
  }

  public hasTeamManagerPermission(organization: Organization, project: Project, skill: Skill) {
    return this.isUserLogged() && (this.hasProjectCreatorPermission(project)
      || this.hasOrganizationCreatorPermission(organization)
      || this.user.skills.find(it => it.name.toLowerCase() == skill.name.toLowerCase() && it.level >= 10));
  }

  public hasOrganizationCreatorPermission(organization: Organization): boolean {
    if (!organization) return false;
    if (this.user.mail == organization.creatorMail)
      return true;
    else
      return false;
  }

  public hasMemberPermission(organization: Organization): boolean {
    var result = false;
    if(this.isUserLogged() && organization != null){
      organization.membersMails.forEach(element => {
        if (element == this.user.mail)
          result = true;
      });
    }
    return result;
  }

  public getToken(): string {
    return this.token;
  }

  public isDarkMode(): boolean {
    return this.darkModeEnabled;
  }

  public toggleDarkMode() {
    this.darkModeEnabled = !this.darkModeEnabled;
    this.storage.set(this.key_darkMode, this.darkModeEnabled);
    document.body.classList.toggle('dark');
  }

  public executeWhenUserLoaded(fun: Function){
    if(this.isUserLogged()){
      fun();
    }else{
      this.whenUserLoaded.push(fun);
    }
  }
}
