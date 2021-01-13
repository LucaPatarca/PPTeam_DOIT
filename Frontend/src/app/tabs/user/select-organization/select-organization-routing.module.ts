import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { SelectOrganizationPage } from './select-organization.page';

const routes: Routes = [
  {
    path: '',
    component: SelectOrganizationPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class SelectOrganizationPageRoutingModule {}
