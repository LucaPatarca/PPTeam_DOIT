import { DataService } from 'src/app/services/data.service';
import { MenuController, Platform } from '@ionic/angular';
import { Component } from '@angular/core';


@Component({
  selector: 'app-home',
  templateUrl: 'home.page.html',
  styleUrls: ['home.page.scss'],
})
export class HomePage {

  HWBackSubscription: any;

  constructor(
    private menuCtrl: MenuController,
    private platform: Platform,
    private dataService:DataService
  ) {
  }

  ionViewDidEnter() {
    this.menuCtrl.enable(true);
    this.HWBackSubscription = this.platform.backButton.subscribe(() => {
      navigator['app'].exitApp();
    });
    
  }

  ionViewDidLeave(){
    this.HWBackSubscription.unsubscribe();
  }

}
