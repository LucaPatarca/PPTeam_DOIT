import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { TabsPage } from './tabs.page';

const routes: Routes = [
  {
    path: 'tabs',
    component: TabsPage,
    children: [
      {
        path: '',
        redirectTo: '/tabs/home',
        pathMatch: 'full',
      },
      {
        path: 'home',
        loadChildren: () => import('../home/home.module').then(m => m.HomePageModule)
      },
      {
        path: 'list-of-projects',
        children: [
          {
            path: '',
            loadChildren: () => import('./project/view/list-of-projects/list-of-projects.module').then(m => m.ListOfProjectsPageModule)
          },
          {
            path: 'view-project',
            loadChildren: () => import('./project/view/view-project/view-project.module').then(m => m.ViewProjectPageModule)
          }
        ]
      },
      {
        path: 'list-of-organizations',
        children: [
          {
            path: '',
            loadChildren: () => import('./organization/view/list-of-organizations/list-of-organizations.module').then(m => m.ListOfOrganizationsPageModule)
          },
          {
            path: 'view-organization',
            loadChildren: () => import('./organization/view/view-organization/view-organization.module').then(m => m.ViewOrganizationPageModule)
          }
        ]
      },
      {
        path: 'view-user',
        loadChildren: () => import('./user/view-user/view-user.module').then(m => m.ViewUserPageModule)
      }
    ]
  },
  {
    path: '',
    redirectTo: '/tabs/home',
    pathMatch: 'full'
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
})
export class TabsPageRoutingModule {}
