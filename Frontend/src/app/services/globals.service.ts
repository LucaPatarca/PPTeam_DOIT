import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class GlobalsService {

  public serverUrl:string = "http://localhost:8080";

  public projectApiUrl:string = this.serverUrl+"/api/projects/";
  public organizationApiUrl:string = this.serverUrl+"/api/organizations/";
  public userApiUrl:string = this.serverUrl+"/api/users/";
  
  public createUserApiUrl = this.userApiUrl + "createNew/";
  public userExistApiUrl = this.userApiUrl + "exist/";
  public userExistSkill = this.userApiUrl + "existSkill/";
  public addCollaborator = this.organizationApiUrl+"addCollaborator/";


  public createProjectApiUrl = this.projectApiUrl + "createNew/";
  public listOfProjectsApiUrl = this.projectApiUrl+"list/";
  public modifyProjectApiUrl = this.projectApiUrl+"update/";
  public existProjectApiUrl = this.projectApiUrl+"exist/";


  public listOfOrganizationsApiUrl = this.organizationApiUrl+"list/";
  public createOrganizationApiUrl = this.organizationApiUrl + "createNew/";
  public existOrganizationApiUrl = this.organizationApiUrl + "exist/";
  public getOrganizationUserCreatorApiUrl = this.organizationApiUrl+"byUser/"; 
  public getOrganizationMember = this.organizationApiUrl+"getUsers/";
  public getUserSkills = this.userApiUrl+"getUserSkills/";
  public modifyOrganizationApiUrl = this.organizationApiUrl+"update/";

  public defaultOrganizationName = "org";

  public userMail:string = "mail";

}