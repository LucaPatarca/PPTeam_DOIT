import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { UserSubmissionPage } from './user-submission.page';

const routes: Routes = [
  {
    path: '',
    component: UserSubmissionPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class UserSubmissionPageRoutingModule {}
