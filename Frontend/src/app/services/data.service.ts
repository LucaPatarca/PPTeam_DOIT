import { Organization } from '../model/organization';
import { Injectable } from '@angular/core';
import { Project } from '../model/project';
import { User } from '../model/user';

@Injectable({
  providedIn: 'root'
})
export class DataService {

  public user:User;
  public selectedOrganization:Organization;
  public projects:Project[];
  public organizations:Organization[];
  public userOrganizations:Organization[];

  constructor() {
    this.projects = new Array();
    this.organizations = new Array();
    this.userOrganizations = new Array();
    this.user=null;
    this.selectedOrganization=null;
  }

  public getUser():User{
    return this.user;
  }

  public isUserLogged():boolean{
    return this.user!=null;
  }

  public setUser(user:User){
    this.user = user;
  }

  public logout(){
    this.user = null;
    this.quitFromOrganization();
  }

  public addProject(project:Project){
    this.projects = this.projects.concat(project);
  }

  public selectOrganization(organization: Organization){
    this.selectedOrganization = organization;
  }

  public isOrganizationSelected():boolean{
    return this.selectedOrganization != null;
  }

  public quitFromOrganization(){
    this.selectedOrganization = null;
  }

  public getProject(id:string) : Project{
    return this.projects.find(it=>it.id==id);
  }

  public updateProject(project:Project){
    const index = this.projects.findIndex(it=>it.id==project.id);
    if(index > -1)
      this.projects[index]=project;
  }

  public removeProject(project:Project){
    const index = this.projects.findIndex(it=>it.id==project.id);
    if(index > -1)
      this.projects = this.projects.splice(index,1);
  }

  public clearProject(){
    this.projects=new Array();
  }

  public addOrganization(organization:Organization){
    this.organizations = this.organizations.concat(organization);
  }

  public addUserOrganization(organization:Organization){
    this.userOrganizations = this.userOrganizations.concat(organization);
  }

  public getOrganization(orgId:string) : Organization{
    return this.organizations.find(it=>it.id==orgId);
  }

  public updateOrganization(organization:Organization){
    const index = this.organizations.findIndex(it=>it.name==organization.id);
    if(index > -1)
      this.organizations[index]=organization;
  }

  public removeOrganization(organization:Organization){
    const index = this.organizations.findIndex(it=>it.name==organization.name);
    if(index > -1)
      this.organizations = this.organizations.splice(index,1);
  }

  public clearOrganizations(){
    this.organizations=new Array();
  }

  public clearUserOrganizations(){
    this.userOrganizations=new Array();
  }

  public isOrganizationsEmpty():boolean{
      return this.organizations.length==0;
  }

  public isUserOrganizationsEmpty():boolean{
    return this.userOrganizations.length==0;
  }

  public isProjectsEmpty():boolean{
    return this.projects.length==0;
  }
}
