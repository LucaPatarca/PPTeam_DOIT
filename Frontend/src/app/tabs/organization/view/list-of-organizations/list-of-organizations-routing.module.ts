import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { ListOfOrganizationsPage } from './list-of-organizations.page';

const routes: Routes = [
  {
    path: '',
    component: ListOfOrganizationsPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ListOfOrganizationsPageRoutingModule {}
