import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { UserSubmissionPageRoutingModule } from './user-submission-routing.module';

import { UserSubmissionPage } from './user-submission.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    UserSubmissionPageRoutingModule
  ],
  declarations: [UserSubmissionPage]
})
export class UserSubmissionPageModule {}
