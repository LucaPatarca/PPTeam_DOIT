import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { ListOfOrganizationsPageRoutingModule } from './list-of-organizations-routing.module';

import { ListOfOrganizationsPage } from './list-of-organizations.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    ListOfOrganizationsPageRoutingModule
  ],
  declarations: [ListOfOrganizationsPage]
})
export class ListOfOrganizationsPageModule {}
