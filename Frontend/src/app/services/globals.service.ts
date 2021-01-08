import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class GlobalsService {
  public organizationApiUrl:string = "http://localhost:8080/api/organizations/";
  public projectApiUrl:string = "http://localhost:8080/api/projects/";
  public UserApiUrl:string = "http://localhost:8080/api/users/";
  public createOrganizationApiUrl = this.organizationApiUrl + "createNew/";


  public createUserApiUrl = this.UserApiUrl + "createNew/";
  public userExistApiUrl = this.UserApiUrl + "exist/";
  

  public createProjectApiUrl = this.projectApiUrl + "createNew/";
  public listOfOrganizationsApiUrl = this.organizationApiUrl+"list/";
  public listOfProjectsApiUrl = this.projectApiUrl+"list/";
  public modifyProjectApiUrl = this.projectApiUrl+"modify/";

  public defaultOrganizationName = "org";


  public userMail:string = "mail";

}