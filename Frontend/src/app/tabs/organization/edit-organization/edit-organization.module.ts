import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule, } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { EditOrganizationPageRoutingModule } from './edit-organization-routing.module';

import { EditOrganizationPage } from './edit-organization.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    IonicModule,
    EditOrganizationPageRoutingModule
  ],
  declarations: [EditOrganizationPage]
})
export class EditOrganizationPageModule {}
