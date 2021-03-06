import { RestService } from 'src/app/services/rest.service';
import { DataService } from 'src/app/services/data.service';
import { Component } from '@angular/core';


import { Platform, ToastController } from '@ionic/angular';
import { SplashScreen } from '@ionic-native/splash-screen/ngx';
import { StatusBar } from '@ionic-native/status-bar/ngx';
import { Network } from '@ionic-native/network/ngx';


@Component({
  selector: 'app-root',
  templateUrl: 'app.component.html',
  styleUrls: ['app.component.scss']
})
export class AppComponent {

  private toast: HTMLIonToastElement;

  constructor(
    private platform: Platform,
    private splashScreen: SplashScreen,
    private statusBar: StatusBar,
    public dataService: DataService,
    private restService: RestService,
    private network: Network,
    private toastCtrl: ToastController,
  ) {
    this.initializeApp();
  }

  initializeApp() {
    this.platform.ready().then(() => {
      this.statusBar.styleDefault();
      this.splashScreen.hide();

      //gestione connessione internet
      if(this.platform.is("mobile")){
        this.network.onConnect().subscribe(
          ()=>{
            this.dataService.isInternetConnected=true;
            if(this.toast)
              this.toast.dismiss();
          }
        );
        this.network.onDisconnect().subscribe(
          ()=>{
            this.dataService.isInternetConnected=false;
            this.toastCtrl.create({
              message: "Non sei connesso a internet!",
              position: "bottom",
              cssClass: "toastAboveTabs"
            }).then(toast=>{
              this.toast = toast;
              toast.present();
            });
          }
        );
        this.dataService.isInternetConnected = this.network.type != "none";
      } else{
        window.addEventListener("online", ()=>{
          console.log("online");
          this.dataService.isInternetConnected=true;
          if(this.toast)
              this.toast.dismiss();
        });
        window.addEventListener("offline", ()=>{
          console.log("offline");
          this.dataService.isInternetConnected=false;
          this.toast.present();
        });
        this.dataService.isInternetConnected = window.navigator.onLine;
        console.log(window.navigator.onLine);
      }
    });
  }
}
