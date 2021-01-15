import { Organization } from '../model/organization';
import { Injectable } from '@angular/core';
import { Project } from '../model/project';

@Injectable({
  providedIn: 'root'
})
export class DataService {
  public listProject:Project[];
  public listOrganization:Organization[];
  public listOrganizationCreator:Organization[];
  public userMail:String;
  public isLog:boolean;
  public orgUser:String;
  public isLogOrg:boolean;

  constructor() {
    this.listProject = new Array();
    this.listOrganization = new Array();
    this.listOrganizationCreator = new Array();
    this.userMail="";
    this.orgUser="";
    this.isLog=false;
    this.isLogOrg=false;
  }

  public getUser():String{
    return this.userMail;
  }

  public userIsLog():boolean{
    
    return this.isLog;
  }

  public setUser(user:String){
    this.userMail = user;
    this.isLog = true;
  }

  public removeUser(){
    this.userMail = "";
    this.isLog = false;
    this.quitFromOrg();
  }

  public addProject(project:Project){
    this.listProject = this.listProject.concat(project);
  }

  public getIsLogOrg():boolean{
    return this.isLogOrg;
  }

  public getProject(id:string) : Project{
    return this.listProject.find(it=>it.id==id);
  }

  public setOrgUser(orgId:string){
    this.orgUser = orgId;
    this.isLogOrg = true;
  }

  public quitFromOrg(){
    this.orgUser = "";
    this.isLogOrg =false;
  }

  public updateProject(oldID:string, project:Project){
    const index = this.listProject.findIndex(it=>it.id==oldID);
    if(index > -1)
      this.listProject[index]=project;
  }

  public removeProject(project:Project){
    const index = this.listProject.findIndex(it=>it.id==project.id);
    if(index > -1)
      this.listProject.splice(index,1);
  }

  public clearProject(){
    this.listProject=new Array();
  }

  public addOrganization(organization:Organization){
    this.listOrganization = this.listOrganization.concat(organization);
  }

  public addOrganizationCreator(organization:Organization){
    this.listOrganizationCreator = this.listOrganizationCreator.concat(organization);
  }

  public getOrganizationt(id:string) : Organization{
    return this.listOrganization.find(it=>it.id==id);
  }

  public updateOrganization(oldName:string, organization:Organization){
    const index = this.listOrganization.findIndex(it=>it.name==oldName);
    if(index > -1)
      this.listOrganization[index]=organization;
  }

  public removeOrganizationt(organization:Organization){
    const index = this.listOrganization.findIndex(it=>it.name==organization.name);
    if(index > -1)
      this.listOrganization.splice(index,1);
  }

  public clearOrganization(){
    this.listOrganization=new Array();
  }

  public clearOrganizationCreator(){
    this.listOrganizationCreator=new Array();
  }

  public isOrganizationEmpty():boolean{
      return this.listOrganization.length==0;
  }

  public isOrganizationCreatorEmpty():boolean{
    return this.listOrganizationCreator.length==0;
  }

  public isProjectEmpty():boolean{
    return this.listProject.length==0;
  }
}