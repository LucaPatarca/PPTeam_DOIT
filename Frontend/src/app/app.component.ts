import { Router } from '@angular/router';
import { Component } from '@angular/core';
import { MenuController } from '@ionic/angular';

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
    private router:Router,
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
    this.router.navigate(['/create-user'], { queryParams: { 'refresh': 1 } });
  }

  listOrganizations(){
    this.router.navigate(['/list-of-organizations'], { queryParams: { 'refresh': 1 } });
  }

  createOrganization(){
    this.menuCtrl.enable(false);
    this.router.navigate(['/create-organization'], { queryParams: { 'refresh': 1 } });
  }

  listProjects(){
    this.router.navigate(['/list-of-projects'], { queryParams: { 'refresh': 1 } });
  }

  createProject(){
    this.menuCtrl.enable(false);
    this.router.navigate(['/create-project'], { queryParams: { 'refresh': 1 } });
  }
  home(){
    this.router.navigate(['/home'], { queryParams: { 'refresh': 1 } });

  }
}
