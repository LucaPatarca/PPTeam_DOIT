import { ActivatedRoute } from '@angular/router';
import { Component } from '@angular/core';
import { ActionSheetController, MenuController, NavController, ToastController } from '@ionic/angular';
import { HttpClient } from '@angular/common/http';
import { Project } from 'src/app/model/project';
import { DataService } from 'src/app/services/data.service';
import { GlobalsService } from 'src/app/services/globals.service';
import { RestService } from 'src/app/services/rest.service';


@Component({
  selector: 'app-view-project',
  templateUrl: './view-project.page.html',
  styleUrls: ['./view-project.page.scss'],
})

export class ViewProjectPage {
  private id: string;
  project: Project;
  loading: boolean;

  constructor(
    private route: ActivatedRoute,
    private menuCtrl: MenuController,
    public nav: NavController,
    private restService: RestService,
    public data: DataService,
    private actionSheetCtrl: ActionSheetController,
  ) {
    this.id = this.route.snapshot.params["id"];
    this.loading = true;
    this.load().then(
      ()=> {
        this.loading = false;
      }
    )
  }

  ionViewDidEnter() {
    this.menuCtrl.enable(false);
  }

  async load() {
    this.project = await this.restService.getProject(this.id);
  }

  goBack() {
    this.nav.navigateBack(["/list-of-projects"], { queryParams: { 'refresh': 1 } });
  }

  modify() {
    this.nav.navigateForward(['/modify-project', { "id": this.project.id }]);
  }

  delete() {
    this.restService.deleteProject(this.id);
    this.goBack();
  }

  public async reload(event?) {
    const newProject = await this.restService.getProject(this.id);
    this.project = newProject; 
    event.target.complete();
  }

  async showActionSheet() {
    const actionSheet = await this.actionSheetCtrl.create({
      header: 'Project',
      cssClass: 'my-custom-class',
      buttons: this.getButtons()
    });
    await actionSheet.present();
  }
  
  getButtons(): Array<Object> {
    var buttons = new Array();

    // azioni per i membri dell'organizzazione
    // TODO da implementare perche non ho l'organizzazione

    // azioni per il creatore dell'organizzazione
    // TODO da implementare perche non ho l'organizzazione

    // azioni per il creatore del progetto
    if (this.data.hasProjectCreatorPermission(this.project)) {
      buttons = buttons.concat([
        {
          text: 'Delete',
          role: 'destructive',
          icon: 'trash',
          handler: () => {
            this.restService.deleteProject(this.project.id);
            this.goBack();
          }
        }, {
          text: 'Close',
          icon: 'close-outline',
          handler: () => {
            this.restService.closeProject(this.project.id);
          }
        }, {
          text: 'Edit',
          icon: 'create-outline',
          handler: () => {
            this.nav.navigateForward(["/modify-project", { "id": this.project.id }]);
          }
        }
      ]);
    }

    // azioni per tutti
    buttons = buttons.concat([
      {
        text: 'Cancel',
        icon: 'close',
        role: 'cancel',
        handler: () => { }
      }
    ]);

    return buttons;
  }

}


