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
  }
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes, { preloadingStrategy: PreloadAllModules })
  ],
  exports: [RouterModule]
})
export class AppRoutingModule { }
