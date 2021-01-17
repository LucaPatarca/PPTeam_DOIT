import { Organization } from './../model/organization';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class GlobalsService {

  public serverUrl:string = "http://localhost:8080"

  public projectApiUrl:string = this.serverUrl+"/api/projects/";

  public UserApiUrl:string = this.serverUrl+"/api/users/";
  public createUserApiUrl = this.UserApiUrl + "createNew/";
  public userExistApiUrl = this.UserApiUrl + "exist/";
  public userExistSkill = this.UserApiUrl + "existSkill/";
  public userAddSkillCollaborator = this.UserApiUrl+"addSkillCollaborator/"


  public createProjectApiUrl = this.projectApiUrl + "createNew/";
  public listOfProjectsApiUrl = this.projectApiUrl+"list/";
  public modifyProjectApiUrl = this.projectApiUrl+"modify/";
  public existProjectApiUrl = this.projectApiUrl+"exist/";


  public organizationApiUrl:string = this.serverUrl+"/api/organizations/";
  public listOfOrganizationsApiUrl = this.organizationApiUrl+"list/";
  public createOrganizationApiUrl = this.organizationApiUrl + "createNew/";
  public existOrganizationApiUrl = this.organizationApiUrl + "exist/";
  public getOrganizationUserCreatorApiUrl = this.organizationApiUrl+"listCreatorOrg/"; 
  public getOrganizationMember = this.organizationApiUrl+"getUsers/";
  public getUserSkills = this.UserApiUrl+"getUserSkills/";
  public modifyOrganizationApiUrl = this.organizationApiUrl+"modify/";


  public defaultOrganizationName = "org";

  public userMail:string = "mail";

}