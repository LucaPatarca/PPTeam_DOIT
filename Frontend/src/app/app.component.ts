import { DataService } from 'src/app/services/data.service';
import { Component } from '@angular/core';
import { MenuController, NavController, AlertController } from '@ionic/angular';


import { Platform } from '@ionic/angular';
import { SplashScreen } from '@ionic-native/splash-screen/ngx';
import { StatusBar } from '@ionic-native/status-bar/ngx';


@Component({
  selector: 'app-root',
  templateUrl: 'app.component.html',
  styleUrls: ['app.component.scss']
})
export class AppComponent {

  isLog:boolean;

  constructor(
    private platform: Platform,
    private splashScreen: SplashScreen,
    private statusBar: StatusBar,
    private menuCtrl:MenuController,
    private navCtrl:NavController,
    private dataService:DataService,
    private alertCtrl:AlertController
  ) {
    if(this.dataService.getUser()!="")
      this.isLog = true;
    else
      this.isLog = false;
    this.initializeApp();
  }

  initializeApp() {
    this.platform.ready().then(() => {
      this.statusBar.styleDefault();
      this.splashScreen.hide();
    });
  }

  

  createUser(){
    this.menuCtrl.enable(false);
    this.navCtrl.navigateRoot(['/create-user'], { queryParams: { 'refresh': 1 } });
  }

  logInUser(){
    this.menuCtrl.enable(false);
    this.navCtrl.navigateRoot(['/login-user'], { queryParams: { 'refresh': 1 } });
  }

  listOrganizations(){
    this.navCtrl.navigateRoot(['/list-of-organizations'], { queryParams: { 'refresh': 1 } });
  }

  createOrganization(){
    this.menuCtrl.enable(false);
    this.navCtrl.navigateRoot(['/create-organization'], { queryParams: { 'refresh': 1 } });
  }

  listProjects(){
    this.navCtrl.navigateRoot(['/list-of-projects'], { queryParams: { 'refresh': 1 } });
  }

  createProject(){
    this.menuCtrl.enable(false);
    this.navCtrl.navigateRoot(['/create-project'], { queryParams: { 'refresh': 1 } });
  }

  selectOrganizationCreator(){
    this.navCtrl.navigateRoot(['select-organization']), { queryParams: { 'refresh': 1 } };
  }

  async quitFromOrg(){
    this.dataService.quitFromOrg();
    const alert = await this.alertCtrl.create({
      cssClass: 'my-custom-class',
      header: 'Selezionata',
      message: 'Organizzazione Selezionata.',
      buttons: ['OK']
    });
  
    await alert.present();
  }

  home(){
    this.navCtrl.navigateRoot(['/home'], { queryParams: { 'refresh': 1 } });
  }

  async logOutUser(){
    this.dataService.removeUser();
    const alert = await this.alertCtrl.create({
      cssClass: 'my-custom-class',
      header: 'Perfetto',
      message: 'LogOut eseguito.',
      buttons: ['OK']
    });
  
    await alert.present();
    this.navCtrl.navigateRoot(['/home']);
  }
}
