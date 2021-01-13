import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class GlobalsService {
  public projectApiUrl:string = "http://localhost:8080/api/projects/";

  public UserApiUrl:string = "http://localhost:8080/api/users/";
  public createUserApiUrl = this.UserApiUrl + "createNew/";
  public userExistApiUrl = this.UserApiUrl + "exist/";
  

  public createProjectApiUrl = this.projectApiUrl + "createNew/";
  public listOfProjectsApiUrl = this.projectApiUrl+"list/";
  public modifyProjectApiUrl = this.projectApiUrl+"modify/";
  public existProjectApiUrl = this.projectApiUrl+"exist/";


  public organizationApiUrl:string = "http://localhost:8080/api/organizations/";
  public defaultOrganizationName = "org";
  public listOfOrganizationsApiUrl = this.organizationApiUrl+"list/";
  public createOrganizationApiUrl = this.organizationApiUrl + "createNew/";
  public existOrganizationApiUrl = this.organizationApiUrl + "exist/";




  public userMail:string = "mail";

}