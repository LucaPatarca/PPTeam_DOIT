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
    loadChildren: () => import('./tabs/project/view/list-of-projects/list-of-projects.module').then( m => m.ListOfProjectsPageModule)
  },
    {
      path: 'view-project',
      loadChildren: () => import('./tabs/project/view/view-project/view-project.module').then( m => m.ViewProjectPageModule)
    },
  {
    path: 'create-project',
    loadChildren: () => import('./tabs/project/create-project/create-project.module').then( m => m.CreateProjectPageModule)
  },
  {
    path: 'create-organization',
    loadChildren: () => import('./tabs/organization/create-organization/create-organization.module').then( m => m.CreateOrganizationPageModule)
  },
  {
    path: 'list-of-organizations',
    loadChildren: () => import('./tabs/organization/view/list-of-organizations/list-of-organizations.module').then( m => m.ListOfOrganizationsPageModule)
  },
  {
    path: 'view-organization',
    loadChildren: () => import('./tabs/organization/view/view-organization/view-organization.module').then( m => m.ViewOrganizationPageModule)
  },
  {
    path: 'modify-project',
    loadChildren: () => import('./tabs/project/modify-project/modify-project.module').then( m => m.ModifyProjectPageModule)
  },
  {
    path: 'add-collaborator',
    loadChildren: () => import('./tabs/organization/expert/add-collaborator/add-collaborator.module').then( m => m.AddCollaboratorPageModule)
  },
  {
    path: 'create-user',
    loadChildren: () => import('./tabs/user/create-user/create-user.module').then( m => m.CreateUserPageModule)
  },
  {
    path: 'login-user',
    loadChildren: () => import('./tabs/user/login-user/login-user.module').then( m => m.LoginUserPageModule)
  },
  {
    path: 'select-organization',
    loadChildren: () => import('./tabs/user/select-organization/select-organization.module').then( m => m.SelectOrganizationPageModule)
  },
  {
    path: 'add-expert',
    loadChildren: () => import('./tabs/organization/expert/add-expert/add-expert.module').then( m => m.AddExpertPageModule)
  },
  {
    path: 'add-skill',
    loadChildren: () => import('./tabs/organization/expert/add-collaborator/add-skill/add-skill.module').then( m => m.AddSkillPageModule)
  },
  {
    path: 'view-skill',
    loadChildren: () => import('./tabs/user/view-skill/view-skill.module').then( m => m.ViewSkillPageModule)
  },  {
    path: 'modify-organization',
    loadChildren: () => import('./tabs/organization/modify-organization/modify-organization.module').then( m => m.ModifyOrganizationPageModule)
  },



  
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes, { preloadingStrategy: PreloadAllModules })
  ],
  exports: [RouterModule]
})
export class AppRoutingModule { }
