import { MenuController } from '@ionic/angular';
import { Component } from '@angular/core';
import { Title }     from '@angular/platform-browser';
import { Storage } from '@ionic/storage';



@Component({
  selector: 'app-home',
  templateUrl: 'home.page.html',
  styleUrls: ['home.page.scss'],
})
export class HomePage {

  log:String;

  constructor(
    private titleService: Title,
    private menuCtrl:MenuController,
    private storage:Storage,
    ) {
      storage.get('user').then((val) => {
        this.log = val;
      });
    // cambio il titolo del pagine
    this.titleService.setTitle("home");
    //attivo il menu 
    this.menuCtrl.enable(true);
  }

}
