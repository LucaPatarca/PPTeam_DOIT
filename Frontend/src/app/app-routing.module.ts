import { NgModule } from '@angular/core';
import { PreloadAllModules, RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  // lista dei path utilizzabili 
  {
    path: 'home',
    loadChildren: () => import('./home/home.module').then( m => m.HomePageModule)
  },
  {
    path: '',
    redirectTo: 'home',
    pathMatch: 'full'
  },
  {
    path: 'list-of-projects',
    loadChildren: () => import('./tabs/list-of-projects/list-of-projects.module').then( m => m.ListOfProjectsPageModule)
  },
    {
      path: 'view-project',
      loadChildren: () => import('./tabs/view-project/view-project.module').then( m => m.ViewProjectPageModule)
    },
  {
    path: 'create-project',
    loadChildren: () => import('./tabs/create-project/create-project.module').then( m => m.CreateProjectPageModule)
  },
  {
    path: 'create-organization',
    loadChildren: () => import('./tabs/create-organization/create-organization.module').then( m => m.CreateOrganizationPageModule)
  },
  {
    path: 'list-of-organizations',
    loadChildren: () => import('./tabs/list-of-organizations/list-of-organizations.module').then( m => m.ListOfOrganizationsPageModule)
  },
  {
    path: 'view-organization',
    loadChildren: () => import('./tabs/view-organization/view-organization.module').then( m => m.ViewOrganizationPageModule)
  },
  
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes, { preloadingStrategy: PreloadAllModules })
  ],
  exports: [RouterModule]
})
export class AppRoutingModule { }
