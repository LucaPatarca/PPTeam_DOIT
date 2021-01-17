import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { ViewSkillPage } from './view-skill.page';

const routes: Routes = [
  {
    path: '',
    component: ViewSkillPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ViewSkillPageRoutingModule {}
