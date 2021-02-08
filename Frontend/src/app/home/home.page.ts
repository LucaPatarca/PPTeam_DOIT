import { DataService } from 'src/app/services/data.service';
import { MenuController, Platform } from '@ionic/angular';
import { Component } from '@angular/core';
import { exit } from 'process';

@Component({
  selector: 'app-home',
  templateUrl: 'home.page.html',
  styleUrls: ['home.page.scss'],
})
export class HomePage {

  constructor(
    private menuCtrl: MenuController,
    private dataService: DataService,
    private platform: Platform,
  ) {
    this.platform.backButton.subscribe(() => {
      exit();
    });
  }

  ionViewWillEnter() {
    this.menuCtrl.enable(true);
  }

}
