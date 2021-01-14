import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { AddExpertPage } from './add-expert.page';

const routes: Routes = [
  {
    path: '',
    component: AddExpertPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class AddExpertPageRoutingModule {}
