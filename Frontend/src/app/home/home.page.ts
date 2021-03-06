import { DataService } from 'src/app/services/data.service';
import { Platform, NavController } from '@ionic/angular';
import { Component } from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: 'home.page.html',
  styleUrls: ['home.page.scss'],
})
export class HomePage {

  HWBackSubscription: any;

  constructor(
    private platform: Platform,
    public dataService:DataService,
    private nav: NavController
  ) {
  }

  ionViewDidEnter() {
    this.HWBackSubscription = this.platform.backButton.subscribe(() => {
      navigator['app'].exitApp();
    });
    
  }

  ionViewDidLeave(){
    this.HWBackSubscription.unsubscribe();
  }

}
