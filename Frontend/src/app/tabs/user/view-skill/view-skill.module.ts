import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { ViewSkillPageRoutingModule } from './view-skill-routing.module';

import { ViewSkillPage } from './view-skill.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    ViewSkillPageRoutingModule
  ],
  declarations: [ViewSkillPage]
})
export class ViewSkillPageModule {}
