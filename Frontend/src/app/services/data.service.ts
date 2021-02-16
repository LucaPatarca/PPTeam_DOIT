import { Organization } from '../model/organization';
import { Injectable } from '@angular/core';
import { User } from '../model/user';
import { Project } from '../model/project';
import { Storage } from '@ionic/storage';
import { isGeneratedFile } from '@angular/compiler/src/aot/util';



@Injectable({
  providedIn: 'root'
})

export class DataService {

  key_user:string = 'user';
  key_organization:string = 'organization';
  key_token:string = 'token';

  private guestUser: User = new User();

  private user:User;
  private organization:Organization;
  private token:string;


  constructor(private storage:Storage) {
    this.user = this.guestUser;
    this.organization = null;
    this.token = null; 
    storage.get(this.key_organization).then((val) => {
      this.organization = val as Organization;
    });
    storage.get(this.key_token).then((val) => {
      this.token = val as string;
    });
    storage.get(this.key_user).then((val) => {
      this.user = val as User;
    });
  }

  public loginUser(user:User){
    this.user = user;
  }

  public getUser():User{
    return this.user;
  }

  public getUserMail():string{
    if(this.user != this.guestUser)
    return this.user.mail;
    return "";
  }

  public isUserLogged():boolean{
    return this.user != this.guestUser;
  }

  public logoutUser(){
    this.storage.remove(this.key_user);
    this.storage.remove(this.key_token);
    this.user = this.guestUser;
    this.token = null;
    this.logoutOrganization();
  }

  public loginOrganization(organization:Organization){
    this.storage.set(this.key_organization,organization);
    this.organization = organization;
  }

  public getOrganization():Organization{
    return this.organization;
  }

  public getOrganizationName():string{
    if(this.organization!=null)
    return this.organization.name;
    return"";
  }

  public isOrganizationSelected():boolean{
    return this.organization != null;
  }

  public logoutOrganization(){
    this.storage.remove(this.key_organization);
    this.organization = null;
  }

  public hasProjectCreatorPermission(project:Project):boolean{
    if(this.user.mail==project.creatorMail)
      return true;
    else 
      return false;
  }

  public hasOrganizationCreatorPermission(organization:Organization):boolean{
    if(this.user.mail == organization.creatorMail)
      return true;
    else
      return false;
  }

  public hasMemberPermission(organization:Organization):boolean{
    organization.membersMails.forEach(element => {
      if(element = this.user.mail) 
        return true;
    });
    return false;
  }

  public getToken():string{
    return this.token;
  }
  
}
