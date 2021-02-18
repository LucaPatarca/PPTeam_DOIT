import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { NavController, ToastController } from '@ionic/angular';
import { Organization } from '../model/organization';
import { OrganizationInformation } from '../model/organization-information';
import { Project } from '../model/project';
import { ProjectInformation } from '../model/project-information';
import { Skill } from '../model/skill';
import { User } from '../model/user';
import { DataService } from './data.service';
import { environment } from 'src/environments/environment';
import { Role } from '../model/role';


@Injectable({
  providedIn: 'root'
})
export class RestService {

  config:any;

  constructor(
    private http: HttpClient,
    private dataService: DataService,
    private toastCtrl: ToastController,
  ) { 
  }


  refreshToken(){
    this.config = {
      headers: { Authorization: this.dataService.getToken() }
    };
  }

  //Organization methods

  async createOrganization(organization: Organization): Promise<Organization> {
    this.refreshToken();
    return new Promise((resolve, rejects) => {
      this.http.post(environment.createOrganizationApiUrl, organization,this.config).subscribe(
        res => {
          this.presentToast("Organizzatione creata");
          resolve(res as unknown as Organization);
        },
        err => {
          this.defaultErrorHandler(err);
          rejects(err);
        }
      );
    });
  }

  async addCollaborator(organizationId: string, userMail: string, skill: Skill): Promise<void> {
    this.refreshToken();
    return new Promise((resolve, rejects) => {
      this.http.post(environment.addCollaborator + organizationId + "/" + userMail, skill,this.config).subscribe(
        res => {
          this.presentToast("Skill Aggiunta");
          resolve();
        },
        err => {
          this.defaultErrorHandler(err);
          rejects(err);
        }
      );
    });
  }

  async getOrganizationMembers(organizationId: string): Promise<User[]> {
    return new Promise((resolve, rejects) => {
      this.http.get(environment.getOrganizationMember + organizationId).subscribe(
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
      this.http.get(environment.listOfOrganizationsApiUrl + page).subscribe(
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
    this.refreshToken();
    return new Promise((resolve, rejects) => {
      this.http.put(environment.modifyOrganizationApiUrl, organization, this.config)
        .subscribe(
          res => {
            this.dataService.updateOrganization(res as unknown as Organization  ); 
            resolve(res as unknown as Organization);
          },
          this.defaultErrorHandler
        );
    });
  }

  async deleteOrganization(organization: Organization): Promise<boolean> {
    this.refreshToken();
    return new Promise((resolve, rejects) => {
      this.http.delete(environment.organizationApiUrl + organization.id,this.config).subscribe(
        res => {
          if(this.dataService.getOrganizationName() == organization.name) {
            this.dataService.logoutOrganization();
          }
          this.presentToast((res as unknown as boolean) == true ? 'Organizzazione Cancellata.' : 'Organizzazione Non Cancellata');
          resolve(res  as unknown as boolean);
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
      this.http.get(environment.organizationApiUrl + id).subscribe(
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

  async getProjectsPage(page: number): Promise<ProjectInformation[]> {
    return new Promise((resolve, rejects) => {
      this.http.get(environment.listOfProjectsApiUrl + page)
        .subscribe(
          res => {
            resolve(res['content'] as ProjectInformation[]);
          },
          err => {
            this.defaultErrorHandler(err);
            rejects(err);
          }
        );
    });
  }

  async createProject(project: Project): Promise<Project> {
    this.refreshToken();
    return new Promise((resolve, rejects) => {
      this.http.post(environment.createProjectApiUrl, project, this.config).subscribe(
        res => {
          this.presentToast('Progetto Creato');
          resolve(res as unknown as Project);
        },
        err => {
          this.defaultErrorHandler(err);
          rejects(err);
        }
      );
    });
  }

  async deleteProject(id: string): Promise<boolean> {
    this.refreshToken();
    return new Promise((resolve, rejects) => {
      this.http.delete(environment.projectApiUrl + id,this.config)
        .subscribe(
          res => {
            this.presentToast("Progetto cancellato");
            resolve(res as unknown as boolean);
          },
          err => {
            this.defaultErrorHandler(err);
            rejects(err);
          }
        );
    });
  }

  async getProject(id: string): Promise<Project> {
    return new Promise((resolve, rejects) => {
      this.http.get(environment.projectApiUrl + id)
        .subscribe(
          res => {
            resolve(res as Project);
          },
          err => {
            this.defaultErrorHandler(err);
            rejects(err);
          }
        );
    });
  }

  async closeProject(id: string): Promise<boolean>{
    return new Promise((resolve,rejects)=>{
      //TODO implementare
    });
  }

  async submit(id: string, role: Role): Promise<boolean>{
    this.refreshToken();
    return new Promise((resolve, rejects) => {
      this.http.post(environment.submitApiUr + id + "/" + this.dataService.getUserMail(), role, this.config)
        .subscribe(
          res => {
            this.presentToast('Submit aggiunta');
            resolve(res as unknown as boolean);
          },
          err => {
            this.defaultErrorHandler(err);
            rejects(err);
          }
        );
    });
  }


  //User methodr

  async createUser(user: User): Promise<User> {
    return new Promise((resolve, rejects) => {
      this.http.post(environment.createUserApiUrl, user, { headers: new HttpHeaders(), responseType: 'json' }).subscribe(
        res => {
          const returnedUser = res as User;
          this.presentToast("Utente " + returnedUser.name + " creato con successo");
          resolve(returnedUser);
        },
        err => {
          this.defaultErrorHandler(err);
          rejects(err);
        }
      );
    });
  }

  async getUser(mail: string): Promise<User> {
    return new Promise((resolve, rejects) => {
      this.http.get(environment.userApiUrl + mail).subscribe(
        res => {
          const user = res as User;
          resolve(user);
        },
        err => {
          this.defaultErrorHandler(err);
          rejects(err);
        }
      );
    });
  }

  async login(user:User): Promise<User>{
    return new Promise((resolve, rejects) => {
      this.http.post(environment.login,user,{observe:'response'}).subscribe(
        res => {
          this.dataService.loginUser(res.body as User, res.headers.get('Authorization'));
          resolve(res.body as User);
        },
        err => {
          this.defaultErrorHandler(err);
          rejects(err);
        }
      );
    });
  }

  async getUserOrganizations(mail: string): Promise<Organization[]> {
    return new Promise((resolve, rejects) => {
      this.http.get(environment.getOrganizationUserCreatorApiUrl + mail)
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

  async getUserSkills(mail: string): Promise<Skill[]> {
    return new Promise((resolve, rejects) => {
      this.http.get(environment.getUserSkills + mail)
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

  async updateProject(project: Project): Promise<Project>{
    this.refreshToken();
    return new Promise((resolve, rejects)=>{
      this.http.put(environment.modifyProjectApiUrl, project, this.config)
      .subscribe(
        res => {
          this.presentToast('Progetto Modificato');
          resolve(res as unknown as Project);
        },
        async err => {
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
    console.log(err);
    this.presentToast(err.error);
  }
}
