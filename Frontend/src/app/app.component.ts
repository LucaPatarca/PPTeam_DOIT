import { RestService } from 'src/app/services/rest.service';
import { DataService } from 'src/app/services/data.service';
import { Component } from '@angular/core';


import { Platform } from '@ionic/angular';
import { SplashScreen } from '@ionic-native/splash-screen/ngx';
import { StatusBar } from '@ionic-native/status-bar/ngx';
import { Network } from '@ionic-native/network/ngx';


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
    public dataService: DataService,
    private restService: RestService,
    private network: Network,
  ) {
    this.initializeApp();
  }

  initializeApp() {
    this.platform.ready().then(() => {
      this.statusBar.styleDefault();
      this.splashScreen.hide();
      if(this.platform.is("mobile")){
        this.network.onConnect().subscribe(
          ()=>this.dataService.isInternetConnected == true
        );
        this.network.onDisconnect().subscribe(
          ()=>this.dataService.isInternetConnected == false
        );
        this.dataService.isInternetConnected = this.network.type != "none";
      } else{
        window.addEventListener("online", ()=>this.dataService.isInternetConnected=true);
        window.addEventListener("offline", ()=>this.dataService.isInternetConnected=false);
        this.dataService.isInternetConnected = window.navigator.onLine;
      }
    });
  }
}
