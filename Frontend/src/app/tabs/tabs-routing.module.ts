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
        children:[
          {
            path:'',
            loadChildren: () => import('../home/home.module').then(m => m.HomePageModule)
          },
          {
            path: 'view-project',
            children: [
              {
                path: '',
                loadChildren: () => import('./project/view/view-project/view-project.module').then(m => m.ViewProjectPageModule)
              },
              {
                path: 'edit-project',
                loadChildren: () => import('./project/edit-project/edit-project.module').then(m => m.EditProjectPageModule)
              }
            ]
          },
          {
            path: 'view-organization',
            children: [
              {
                path: '',
                loadChildren: () => import('./organization/view/view-organization/view-organization.module').then(m => m.ViewOrganizationPageModule)
              },
              {
                path: 'edit-organization',
                loadChildren: () => import('./organization/edit-organization/edit-organization.module').then(m => m.EditOrganizationPageModule)
              }
            ]
          }
        ]
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
            children: [
              {
                path: '',
                loadChildren: () => import('./project/view/view-project/view-project.module').then(m => m.ViewProjectPageModule)
              },
              {
                path: 'edit-project',
                loadChildren: () => import('./project/edit-project/edit-project.module').then(m => m.EditProjectPageModule)
              }
            ]
          },
          {
            path:'create-project',
            loadChildren: () => import('../tabs/project/edit-project/edit-project.module').then(m => m.EditProjectPageModule)
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
            children: [
              {
                path: '',
                loadChildren: () => import('./organization/view/view-organization/view-organization.module').then(m => m.ViewOrganizationPageModule)
              },
              {
                path: 'edit-organization',
                loadChildren: () => import('./organization/edit-organization/edit-organization.module').then(m => m.EditOrganizationPageModule)
              }
            ]
          },
          {
            path:'create-organization',
            loadChildren: () => import('../tabs/organization/create-organization/create-organization.module').then(m => m.CreateOrganizationPageModule)
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
export class TabsPageRoutingModule { }
