import { UserSubmissionInformation } from 'src/app/model/UserSubmissionInformation';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ToastController } from '@ionic/angular';
import { Organization } from '../model/organization';
import { OrganizationInformation } from '../model/organization-information';
import { Project } from '../model/project';
import { ProjectInformation } from '../model/project-information';
import { Skill } from '../model/skill';
import { User } from '../model/user';
import { DataService } from './data.service';
import { environment } from 'src/environments/environment';
import { Role } from '../model/role';
import { Page } from '../model/page';

@Injectable({
  providedIn: 'root'
})
export class RestService {

  constructor(
    private http: HttpClient,
    private dataService: DataService,
    private toastCtrl: ToastController,
  ) { 
  }

  getConfig():Object{
    return {
      headers: { Authorization: this.dataService.getToken() }
    };
  }

  //Organization methods

  async createOrganization(organization: Organization): Promise<Organization> {
    return new Promise((resolve, rejects) => {
      this.http.post(environment.createOrganizationApiUrl, organization,this.getConfig()).subscribe(
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
    return new Promise((resolve, rejects) => {
      this.http.post(environment.addCollaborator + organizationId + "/" + userMail, skill,this.getConfig()).subscribe(
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

  async getProjectsByOrganization(organizationId: string): Promise<Project[]>{
    return new Promise((resolve, rejects) => {
      this.http.get(environment.getProjectsByOrganization + organizationId).subscribe(
        res => {
          resolve(res as Project[]);
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
    return new Promise((resolve, rejects) => {
      this.http.put(environment.modifyOrganizationApiUrl, organization, this.getConfig())
        .subscribe(
          res => {
            resolve(res as unknown as Organization);
          },
          err=>{
            this.defaultErrorHandler(err);
            rejects(err);
          }
        );
    });
  }

  async deleteOrganization(organization: Organization): Promise<boolean> {
    return new Promise((resolve, rejects) => {
      this.http.delete(environment.organizationApiUrl + organization.id,this.getConfig()).subscribe(
        res => {
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

  async addMember(organizationId: string, userMail:string): Promise<Boolean>{
    return new Promise((resolve, rejects) => {
      this.http.post(environment.addMember+organizationId +"/"+ userMail, "", this.getConfig()).subscribe(
        res => {
          resolve(res as unknown as Boolean);
        },
        err => {
          this.defaultErrorHandler(err);
          rejects(err);
        }
      );
    });
  }

  async removeMember(organizationId: string, userMail:string, removeProjects: boolean){
    return new Promise((resolve, rejects) => {
      this.http.post(environment.removeMember+organizationId +"/"+ userMail + "/" + removeProjects as string, "", this.getConfig()).subscribe(
        res => {
          resolve(res as unknown as boolean);
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
            rejects(err);
          }
        );
    });
  }

  async createProject(project: Project): Promise<Project> {
    return new Promise((resolve, rejects) => {
      this.http.post(environment.createProjectApiUrl, project, this.getConfig()).subscribe(
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
    return new Promise((resolve, rejects) => {
      this.http.delete(environment.projectApiUrl + id,this.getConfig())
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
    return new Promise((resolve, rejects) => {
      this.http.put(environment.closeProject + id,"", this.getConfig())
        .subscribe(
          res => {
            resolve(res as unknown as boolean);
          },
          err => {
            this.defaultErrorHandler(err);
            rejects(err);
          }
        );
    });
  }

  async submit(id: string, role: Role): Promise<boolean>{
    return new Promise((resolve, rejects) => {
      this.http.post(environment.submitApiUrl + id, role, this.getConfig())
        .subscribe(
          res => {
            resolve(res as unknown as boolean);
          },
          err => {
            this.defaultErrorHandler(err);
            rejects(err);
          }
        );
    });
  }

  async rejectCandidate(projectId: string, role: Role): Promise<boolean>{
    return new Promise((resolve, rejects)=>{
      this.http.post(environment.rejectCandidate + projectId, role, this.getConfig())
        .subscribe(
          res => {
            resolve(res as unknown as boolean);
          },
          err => {
            this.defaultErrorHandler(err);
            rejects(err);
          }
        );
    });
  }

  async removeTeamMember(projectId: string, role: Role): Promise<boolean>{
    return new Promise((resolve, rejects)=>{
      this.http.post(environment.removeTeamMember + projectId, role, this.getConfig())
        .subscribe(
          res => {
            resolve(res as unknown as boolean);
          },
          err => {
            this.defaultErrorHandler(err);
            rejects(err);
          }
        );
    });
  }

  async acceptCandidate(projectId: string, role: Role): Promise<boolean>{
    return new Promise((resolve, rejects)=>{
      this.http.post(environment.acceptCandidate + projectId, role, this.getConfig())
        .subscribe(
          res => {
            resolve(res as unknown as boolean);
          },
          err => {
            this.defaultErrorHandler(err);
            rejects(err);
          }
        );
    });
  }

  async getUserProjects(userMail: string): Promise<Project[]>{
    return new Promise((resolve, rejects)=>{
      this.http.get(environment.getUserProjects + userMail)
        .subscribe(
          res=>{
            resolve(res as Project[]);
          },
          err=>{
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

  async getUserPage(page: number): Promise<Page<User>> {
    return new Promise((resolve, rejects) => {
      this.http.get(environment.listOfUsersApiUrl + page).subscribe(
        res => {
          resolve(res as Page<User>);
        },
        err => {
          rejects(err);
        }
      );
    });
  }

  async getExpertPage(page: number): Promise<User[]> {
    return new Promise((resolve, rejects) => {
      this.http.get(environment.getExpertsPage + page).subscribe(
        res => {
          resolve(res['content'] as User[]);
        },
        err => {
          rejects(err);
        }
      );
    });
  }

  async existUser(userMail: string): Promise<boolean> {
    return new Promise((resolve, rejects) => {
      this.http.get(environment.existUserApiUrl + userMail).subscribe(
        res => {
          resolve(res['content'] as unknown as boolean);
        },
        err => {
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
    return await new Promise((resolve, rejects) => {
      this.http.get(environment.getOrganizationByUserApiUrl + mail)
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
    return new Promise((resolve, rejects)=>{
      this.http.put(environment.modifyProjectApiUrl, project, this.getConfig())
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

  async getUserSubmissions():Promise<UserSubmissionInformation[]>{
    const url = environment.getUserSubmissions+this.dataService.getUserMail();
    return new Promise((resolve, rejects)=>{
      this.http.get(url, this.getConfig())
      .subscribe(
        res => {
          resolve(res as unknown as UserSubmissionInformation[]);
        },
        async err => {
          this.defaultErrorHandler(err);
          rejects(err);
        }
      );
    });
  }

  async rejectSubmission(userSubmission:UserSubmissionInformation,role:Role):Promise<boolean>{
    return new Promise((resolve, rejects)=>{
      this.http.post(environment.rejectSubmission+userSubmission.projectId,role, this.getConfig())
      .subscribe(
        res => {
          resolve(res as unknown as boolean);
        },
        async err => {
          this.defaultErrorHandler(err);
          rejects(err);
        }
      );
    });
  }

  async addNewSkill(skill:string){
    return new Promise((resolve, rejects)=>{
      this.http.post(environment.addNewSkill+skill,this.dataService.getUserMail(), this.getConfig())
      .subscribe(
        async res => {
          resolve(res as unknown as boolean);
          this.refreshUser();
        },
        async err => {
          this.defaultErrorHandler(err);
          rejects(err);
        }
      );
    });
  }

  async removeSkill(skill:Skill){
    return new Promise((resolve, rejects)=>{
      this.http.post(environment.removeSkill+this.dataService.getUserMail(),skill, this.getConfig())
      .subscribe(
        async res => {
          resolve(res as unknown as boolean);
          this.refreshUser();
        },
        async err => {
          this.defaultErrorHandler(err);
          rejects(err);
        }
      );
    });
  }

  async refreshToken(){
    this.http.get(environment.refreshToken,this.getConfig()).toPromise()
      .then(res=>{
        this.dataService.refreshToken(res["token"]);
      }).catch(err=>{
        if(err.status == 401)
          this.dataService.logoutUser();
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
  }

  refreshUser(){
    this.getUser(this.dataService.getUserMail()).then(
      val=>this.dataService.refreshUser(val)
    );
  }
}
