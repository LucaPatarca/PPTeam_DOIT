import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { ModifyProjectPageRoutingModule } from './modify-project-routing.module';

import { ModifyProjectPage } from './modify-project.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    ModifyProjectPageRoutingModule
  ],
  declarations: [ModifyProjectPage]
})
export class ModifyProjectPageModule {}
