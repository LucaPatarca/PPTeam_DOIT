import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { ListOfProjectsPageRoutingModule } from './list-of-projects-routing.module';

import { ListOfProjectsPage } from './list-of-projects.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    ListOfProjectsPageRoutingModule
  ],
  declarations: [ListOfProjectsPage]
})
export class ListOfProjectsPageModule {}
