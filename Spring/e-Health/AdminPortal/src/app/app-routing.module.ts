import {ModuleWithProviders} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from './login/login.component'
import {UserAccountComponent} from './user-account/user-account.component';
import {SaveExportComponent} from './save-export/save-export.component';
import {AuthGuard} from './auth.guard';

const routes: Routes = [
  {
    path: '',
    redirectTo: '/login',
    pathMatch: 'full'
    //path: '',
    //component:LoginComponent
  },
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'patientAccount',
    component: UserAccountComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'saveExport',
    component: SaveExportComponent,
    canActivate: [AuthGuard]
  },
];

// @NgModule({
//   imports: [RouterModule.forRoot(routes),CommonModule],
//   exports: [RouterModule]
// })


//export class AppRoutingModule { }
export const routing: ModuleWithProviders<RouterModule> = RouterModule.forRoot(routes, {onSameUrlNavigation: 'reload'});
