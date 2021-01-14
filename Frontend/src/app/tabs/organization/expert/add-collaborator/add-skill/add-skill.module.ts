import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { AddSkillPageRoutingModule } from './add-skill-routing.module';

import { AddSkillPage } from './add-skill.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    ReactiveFormsModule,
    AddSkillPageRoutingModule
  ],
  declarations: [AddSkillPage]
})
export class AddSkillPageModule {}
