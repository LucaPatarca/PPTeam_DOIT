import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { ModifyOrganizationPage } from './modify-organization.page';

const routes: Routes = [
  {
    path: '',
    component: ModifyOrganizationPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ModifyOrganizationPageRoutingModule {}
