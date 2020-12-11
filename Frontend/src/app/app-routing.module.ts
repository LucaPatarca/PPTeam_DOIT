import { NgModule } from '@angular/core';
import { PreloadAllModules, RouterModule, Routes } from '@angular/router';

const routes: Routes = [
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


];

@NgModule({
  imports: [
    RouterModule.forRoot(routes, { preloadingStrategy: PreloadAllModules })
  ],
  exports: [RouterModule]
})
export class AppRoutingModule { }
