import { TabsPage } from './tabs/tabs.page';
import { ViewUserPage } from './tabs/user/view-user/view-user.page';
import { ListOfOrganizationsPage } from './tabs/organization/view/list-of-organizations/list-of-organizations.page';
import { ListOfProjectsPage } from './tabs/project/view/list-of-projects/list-of-projects.page';
import { AppComponent } from './app.component';
import { HomePage } from './home/home.page';
import { NgModule } from '@angular/core';
import { PreloadAllModules, RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  {
    path: '',
    loadChildren: () => import('./tabs/tabs.module').then(m => m.TabsPageModule)
  },
  {
    path: 'edit-project',
    loadChildren: () => import('./tabs/project/edit-project/edit-project.module').then(m => m.EditProjectPageModule)
  },
  {
    path: 'select-organization',
    loadChildren: () => import('./tabs/user/select-organization/select-organization.module').then(m => m.SelectOrganizationPageModule)
  },
  {
    path: 'modify-organization',
    loadChildren: () => import('./tabs/organization/modify-organization/modify-organization.module').then(m => m.ModifyOrganizationPageModule)
  }

];

@NgModule({
  imports: [
    RouterModule.forRoot(routes, { preloadingStrategy: PreloadAllModules })
  ],
  exports: [RouterModule]
})
export class AppRoutingModule { }
