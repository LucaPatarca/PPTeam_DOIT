import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { AddCollaboratorPage } from './add-collaborator.page';

const routes: Routes = [
  {
    path: '',
    component: AddCollaboratorPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class AddCollaboratorPageRoutingModule {}
