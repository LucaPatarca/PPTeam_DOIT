import { Organization } from '../model/organization';
import { Injectable } from '@angular/core';
import { User } from '../model/user';
import { Project } from '../model/project';

@Injectable({
  providedIn: 'root'
})
export class DataService {

  public user: User;
  public selectedOrganization: Organization;

  private guestUser: User = new User();

  constructor() {
    this.user = this.guestUser;
    this.selectedOrganization = null;
  }

  public getUser(): User {
    return this.user;
  }

  public isUserLogged(): boolean {
    return this.user != this.guestUser;
  }

  public setUser(user: User) {
    this.user = user;
  }

  public logout() {
    this.user = this.guestUser;
    this.quitFromOrganization();
  }

  public getSelectedOrganization(): Organization {
    return this.selectedOrganization;
  }

  public selectOrganization(organization: Organization) {
    this.selectedOrganization = organization;
  }

  public isOrganizationSelected(): boolean {
    return this.selectedOrganization != null;
  }

  public quitFromOrganization() {
    this.selectedOrganization = null;
  }

  hasMemberPermission(organization: Organization): boolean {
    if (organization == null || organization == undefined) return false;
    return organization.membersMails.includes(this.user.mail);
  }

  hasOrganizationCreatorPermission(organization: Organization): boolean {
    if (organization == null || organization == undefined) return false;
    return organization.creatorMail == this.user.mail;
  }

  hasProjectCreatorPermission(project: Project){
    if(project == null || project == undefined) return false;
    return project.creatorMail == this.user.mail;
  }
}
