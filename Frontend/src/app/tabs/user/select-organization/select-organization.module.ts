import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { SelectOrganizationPageRoutingModule } from './select-organization-routing.module';

import { SelectOrganizationPage } from './select-organization.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    SelectOrganizationPageRoutingModule
  ],
  declarations: [SelectOrganizationPage]
})
export class SelectOrganizationPageModule {}
