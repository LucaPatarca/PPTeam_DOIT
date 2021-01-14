import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { AddCollaboratorPage } from './add-collaborator.page';

const routes: Routes = [
  {
    path: '',
    component: AddCollaboratorPage
  },  {
    path: 'add-skill',
    loadChildren: () => import('./add-skill/add-skill.module').then( m => m.AddSkillPageModule)
  }

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class AddCollaboratorPageRoutingModule {}
