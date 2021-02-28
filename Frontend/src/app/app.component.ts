import { DataService } from 'src/app/services/data.service';
import { Component } from '@angular/core';
import { NavController, ToastController } from '@ionic/angular';


import { Platform } from '@ionic/angular';
import { SplashScreen } from '@ionic-native/splash-screen/ngx';
import { StatusBar } from '@ionic-native/status-bar/ngx';


@Component({
  selector: 'app-root',
  templateUrl: 'app.component.html',
  styleUrls: ['app.component.scss']
})
export class AppComponent {

  isLog: boolean;

  constructor(
    private platform: Platform,
    private splashScreen: SplashScreen,
    private statusBar: StatusBar,
    private navCtrl: NavController,
    public dataService: DataService,
    private toastCtrl: ToastController
  ) {
    this.initializeApp();
  }

  initializeApp() {
    this.platform.ready().then(() => {
      this.statusBar.styleDefault();
      this.splashScreen.hide();
    });
  }



  createUser() {
    this.navCtrl.navigateForward(['/create-user'], { queryParams: { 'refresh': 1 } });
  }

  logInUser() {
    this.navCtrl.navigateForward(['/login-user'], { queryParams: { 'refresh': 1 } });
  }

  listOrganizations() {
    this.navCtrl.navigateRoot(['/list-of-organizations'], { queryParams: { 'refresh': 1 } });
  }

  createOrganization() {
    this.navCtrl.navigateForward(['/create-organization'], { queryParams: { 'refresh': 1 } });
  }

  listProjects() {
    this.navCtrl.navigateRoot(['/list-of-projects'], { queryParams: { 'refresh': 1 } });
  }

  createProject() {
    this.navCtrl.navigateForward(['/edit-project'], { queryParams: { 'refresh': 1 } });
  }

  selectOrganizationCreator() {
    this.navCtrl.navigateForward(['select-organization'], { queryParams: { 'refresh': 1 } });
  }

  async quitFromOrg() {
    let orgName = this.dataService.getOrganization().name;
    this.dataService.logoutOrganization();
    const toast = await this.toastCtrl.create({
      message: 'Logout da ' + orgName + ' eseguito',
      duration: 2000
    });

    toast.present();
  }

  home() {
    this.navCtrl.navigateRoot(['/home'], { queryParams: { 'refresh': 1 } });
  }

  async logOutUser() {
    this.dataService.logoutUser();
    const toast = await this.toastCtrl.create({
      message: 'Logout eseguito',
      duration: 2000
    });
    toast.present();
    this.navCtrl.navigateRoot(['/home'], { queryParams: { 'refresh': 1 } });
  }

  viewSkill() {
    this.navCtrl.navigateForward(['view-skill'], { queryParams: { 'refresh': 1 } });
  }

  userSubmission(){
    this.navCtrl.navigateRoot(['/user-submission'], { queryParams: { 'refresh': 1 } });
  }
}
