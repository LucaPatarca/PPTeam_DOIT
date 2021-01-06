import { Injectable } from '@angular/core';
import { Project } from '../model/project';

@Injectable({
  providedIn: 'root'
})
export class DataService {
  public list:Project[];
  constructor() {
    this.list = new Array();
  }

  public addProject(project:Project){
    this.list = this.list.concat(project);
  }

  public getProject(id:string) : Project{
    return this.list.find(it=>it.id==id);
  }

  public updateProject(oldID:string, project:Project){
    const index = this.list.findIndex(it=>it.id==oldID);
    if(index > -1)
      this.list[index]=project;
  }

  public removeProject(project:Project){
    const index = this.list.findIndex(it=>it.id==project.id);
    if(index > -1)
      this.list.splice(index,1);
  }

  public clear(){
    this.list=new Array();
  }
}
