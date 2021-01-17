import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { AddExpertPageRoutingModule } from './add-expert-routing.module';

import { AddExpertPage } from './add-expert.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    AddExpertPageRoutingModule
  ],
  declarations: [AddExpertPage]
})
export class AddExpertPageModule {}
