import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { ViewOrganizationPageRoutingModule } from './view-organization-routing.module';

import { ViewOrganizationPage } from './view-organization.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    ViewOrganizationPageRoutingModule
  ],
  declarations: [ViewOrganizationPage]
})
export class ViewOrganizationPageModule {}
