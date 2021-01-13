import { Organization } from '../model/organization';
import { Injectable } from '@angular/core';
import { Project } from '../model/project';

@Injectable({
  providedIn: 'root'
})
export class DataService {
  public listProject:Project[];
  public listOrganization:Organization[];
  constructor() {
    this.listProject = new Array();
    this.listOrganization = new Array();
  }

  public addProject(project:Project){
    this.listProject = this.listProject.concat(project);
  }

  public getProject(id:string) : Project{
    return this.listProject.find(it=>it.id==id);
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

  public getOrganizationt(name:string) : Organization{
    return this.listOrganization.find(it=>it.name==name);
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
}
