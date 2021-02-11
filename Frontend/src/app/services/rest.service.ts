import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { NavController, ToastController } from '@ionic/angular';
import { Organization } from '../model/organization';
import { OrganizationInformation } from '../model/organization-information';
import { Skill } from '../model/skill';
import { User } from '../model/user';
import { DataService } from './data.service';
import { GlobalsService } from './globals.service';

@Injectable({
  providedIn: 'root'
})
export class RestService {

  constructor(
    private http: HttpClient,
    private dataService: DataService,
    private navCtrl: NavController,
    private globals: GlobalsService,
    private toastCtrl: ToastController
  ) { }

  //Organization methods

  async createOrganization(organization: Organization): Promise<Organization> {
    return new Promise((resolve, rejects) => {
      this.http.post(this.globals.createOrganizationApiUrl, organization, { headers: new HttpHeaders(), responseType: 'json' }).subscribe(
        res => {
          this.presentToast("Organizzatione creata");
          resolve(res as Organization);
        },
        err => {
          this.defaultErrorHandler(err);
          rejects(err);
        }
      );
    });
  }

  async addCollaborator(organizationId: string, userMail: string, skill: Skill): Promise<void> {
    return new Promise((resolve, rejects) => {
      this.http.post(this.globals.addCollaborator + organizationId + "/" + userMail, skill, { headers: new HttpHeaders(), responseType: 'json' }).subscribe(
        res => {
          this.presentToast("Skill Aggiunta");
          resolve();
        },
        err=>{
          this.defaultErrorHandler(err);
          rejects(err);
        }
      );
    });
  }

  async getOrganizationMembers(organizationId: string): Promise<User[]> {
    return new Promise((resolve, rejects) => {
      this.http.get(this.globals.getOrganizationMember + organizationId).subscribe(
        res => {
          resolve(res as User[]);
        },
        err => {
          rejects(err);
        }
      )
    });
  }

  async getOrganizationPage(page: number): Promise<OrganizationInformation[]> {
    return new Promise((resolve, rejects) => {
      this.http.get(this.globals.listOfOrganizationsApiUrl + page).subscribe(
        res => {
          resolve(res['content'] as OrganizationInformation[]);
        },
        err => {
          rejects(err);
        }
      );
    });
  }

  async modifyOrganization(organization: Organization): Promise<Organization> {
    return new Promise((resolve, rejects) => {
      this.http.put(this.globals.modifyOrganizationApiUrl, organization, { headers: new HttpHeaders(), responseType: 'json' })
        .subscribe(
          res => {
            resolve(res as Organization);
          },
          this.defaultErrorHandler
        );
    });
  }

  async deleteOrganization(organization: Organization): Promise<boolean> {
    return new Promise((resolve, rejects) => {
      this.http.delete(this.globals.organizationApiUrl + organization.id).subscribe(
        res => {
          if (organization.creatorMail == this.dataService.user.mail) {
            this.dataService.quitFromOrganization();
          }
          this.presentToast(res == true ? 'Organizzazione Cancellata.' : 'Organizzazione Non Cancellata');
          resolve(res as boolean);
        },
        err => {
          this.defaultErrorHandler(err);
          rejects(err);
        }
      );
    });
  }

  async getOrganization(id: string): Promise<Organization> {
    return new Promise((resolve, rejects) => {
      this.http.get(this.globals.organizationApiUrl + id).subscribe(
        res => {
          resolve(res as Organization);
        },
        err => {
          this.defaultErrorHandler(err);
          rejects(err);
        }
      );
    });
  }

  //Project methods

  //User methods

  async createUser(user: User) : Promise<User>{
    return new Promise((resolve, rejects)=>{
      this.http.post(this.globals.createUserApiUrl, user, { headers: new HttpHeaders(), responseType: 'json' }).subscribe(
        res=>{
          const returnedUser = res as User;
          this.presentToast("Utente " + returnedUser.name + " creato con successo");
          resolve(returnedUser);
        },
        err=>{
          this.defaultErrorHandler(err);
          rejects(err);
        }
      );
    });
  }

  async getUser(mail: string): Promise<User>{
    return new Promise((resolve, rejects)=>{
      this.http.get(this.globals.userApiUrl + mail).subscribe(
        res=>{
          const user = res as User;
          resolve(user);
        },
        err=>{
          this.defaultErrorHandler(err);
          rejects(err);
        }
      );
    });
  }

  async getUserOrganizations(mail: string): Promise<Organization[]>{
    return new Promise((resolve, rejects)=>{
      this.http.get(this.globals.getOrganizationUserCreatorApiUrl + mail)
      .subscribe(
        res => {
          resolve(res as Organization[]);
        },
        err => {
          rejects(err);
        }
      );
    });
  }

  async getUserSkills(mail: string): Promise<Skill[]>{
    return new Promise((resolve, rejects)=>{
      this.http.get(this.globals.getUserSkills + mail)
      .subscribe(
        res => {
          resolve(res as Skill[]);
        },
        err => {
          this.defaultErrorHandler(err);
          rejects(err);
        }
      );
    });
  }

  //Utils methods

  async presentToast(message: string) {
    const toast = await this.toastCtrl.create({
      message: message,
      duration: 2000
    });
    toast.present();
  }

  async defaultErrorHandler(err: any) {
    this.presentToast(err.message);
  }
}
