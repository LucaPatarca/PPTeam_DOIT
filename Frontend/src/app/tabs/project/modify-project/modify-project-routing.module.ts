import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { ModifyProjectPage } from './modify-project.page';

const routes: Routes = [
  {
    path: '',
    component: ModifyProjectPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ModifyProjectPageRoutingModule {}
