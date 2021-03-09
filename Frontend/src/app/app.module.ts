import { YourOrganizationsComponent } from './components/your-organizations/your-organizations.component';
import { YourProjectsComponent } from './components/your-projects/your-projects.component';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouteReuseStrategy } from '@angular/router';
import { FormBuilder ,FormsModule ,ReactiveFormsModule  } from '@angular/forms';
import { IonicStorageModule  } from '@ionic/storage';


import { IonicModule, IonicRouteStrategy } from '@ionic/angular';
import { SplashScreen } from '@ionic-native/splash-screen/ngx';
import { StatusBar } from '@ionic-native/status-bar/ngx';

import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';

import {HttpClientModule} from "@angular/common/http";
import { ServiceWorkerModule } from '@angular/service-worker';
import { environment } from '../environments/environment';
import { SelectUserComponent } from './components/select-user/select-user.component';
import { Network } from '@ionic-native/network/ngx';


@NgModule({
  declarations: [AppComponent, SelectUserComponent,YourProjectsComponent,YourOrganizationsComponent],
  entryComponents: [SelectUserComponent,YourProjectsComponent,YourOrganizationsComponent],
  imports: [
    BrowserModule,
    IonicModule.forRoot(),
    ReactiveFormsModule,
    FormsModule,
    AppRoutingModule,
    HttpClientModule,
    IonicStorageModule.forRoot(),
    ServiceWorkerModule.register('ngsw-worker.js', { enabled: environment.production })
    ],
  providers: [
    FormBuilder,
    StatusBar,
    SplashScreen,
    Network,
    { provide: RouteReuseStrategy, useClass: IonicRouteStrategy },
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
  constructor(){}
}
