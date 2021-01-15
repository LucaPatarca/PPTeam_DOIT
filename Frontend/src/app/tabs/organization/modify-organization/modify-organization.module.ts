import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule, } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { ModifyOrganizationPageRoutingModule } from './modify-organization-routing.module';

import { ModifyOrganizationPage } from './modify-organization.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    IonicModule,
    ModifyOrganizationPageRoutingModule
  ],
  declarations: [ModifyOrganizationPage]
})
export class ModifyOrganizationPageModule {}
