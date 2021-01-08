import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { AddCollaboratorPageRoutingModule } from './add-collaborator-routing.module';

import { AddCollaboratorPage } from './add-collaborator.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    AddCollaboratorPageRoutingModule
  ],
  declarations: [AddCollaboratorPage]
})
export class AddCollaboratorPageModule {}
