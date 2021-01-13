import { Router } from '@angular/router';
import { Component } from '@angular/core';
import { MenuController, NavController } from '@ionic/angular';

import { Platform } from '@ionic/angular';
import { SplashScreen } from '@ionic-native/splash-screen/ngx';
import { StatusBar } from '@ionic-native/status-bar/ngx';

@Component({
  selector: 'app-root',
  templateUrl: 'app.component.html',
  styleUrls: ['app.component.scss']
})
export class AppComponent {
  constructor(
    private platform: Platform,
    private splashScreen: SplashScreen,
    private statusBar: StatusBar,
    private menuCtrl:MenuController,
    private navCtrl:NavController,
  ) {
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
  home(){
    this.navCtrl.navigateRoot(['/home'], { queryParams: { 'refresh': 1 } });

  }
}
