import { ActivatedRoute } from '@angular/router';
import { Component } from '@angular/core';
import { MenuController, NavController, ToastController } from '@ionic/angular';
import { HttpClient } from '@angular/common/http';
import { Project } from 'src/app/model/project';
import { DataService } from 'src/app/services/data.service';
import { GlobalsService } from 'src/app/services/globals.service';


@Component({
  selector: 'app-view-project',
  templateUrl: './view-project.page.html',
  styleUrls: ['./view-project.page.scss'],
})

export class ViewProjectPage {
  isClosed: string;
  project: Project;
  emptyNeededskills: String;
  emptyCandidates: String;
  constructor(
    private route: ActivatedRoute,
    private menuCtrl: MenuController,
    public nav: NavController,
    private http: HttpClient,
    public data: DataService,
    private globals: GlobalsService,
    private toastCtrl: ToastController
  ) {
    const id = this.route.snapshot.params["id"];
    if (this.project.closed) {
      this.isClosed = "closed";
    } else {
      this.isClosed = "opened";
    }
  }

  ionViewDidEnter() {
    this.menuCtrl.enable(false);
  }

  goBack() {
    this.nav.navigateBack(["/list-of-projects"], { queryParams: { 'refresh': 1 } })
  }

  modify() {
    this.nav.navigateForward(['/modify-project', { "id": this.project.id }]);
  }

  delete() {
    this.http.delete(this.globals.projectApiUrl + this.project.id)
      .subscribe(
        async res => {
          console.log('Delete successful Project with Id: ' + this.project.id);
          const toast = await this.toastCtrl.create({
            message: 'Progetto Cancellato',
            duration: 2000
          });
          toast.present();
        },
        async err => {
          const toast = await this.toastCtrl.create({
            message: err.error,
            duration: 2000
          });
          toast.present();
        }
      );
    this.goBack();
  }

  public reload(event?) {
    this.http.get(this.globals.projectApiUrl + this.project.id).subscribe(
      res => {
        if (res != null) {
          const reloadedProject: Project = res as Project;
          this.project = reloadedProject;
          if (this.project.closed) {
            this.isClosed = "closed";
          } else {
            this.isClosed = "opened";
          }
        }
      },
      err => {
        console.log(err);
      }
    );
    event.target.complete();
  }


}


