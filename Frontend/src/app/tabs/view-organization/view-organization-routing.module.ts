import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { ViewOrganizationPage } from './view-organization.page';

const routes: Routes = [
  {
    path: '',
    component: ViewOrganizationPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ViewOrganizationPageRoutingModule {}
