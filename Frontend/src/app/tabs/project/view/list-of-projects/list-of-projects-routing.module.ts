import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { ListOfProjectsPage } from './list-of-projects.page';

const routes: Routes = [
  {
    path: '',
    component: ListOfProjectsPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ListOfProjectsPageRoutingModule {}
