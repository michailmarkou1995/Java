import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ModuleWithProviders }  from '@angular/core';
import { CommonModule } from '@angular/common';
import {LoginComponent} from './login/login.component'
import { UserAccountComponent } from './user-account/user-account.component';
import { SaveExportComponent } from './save-export/save-export.component';
import { AuthGuard } from './auth.guard';

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
    path: 'datesDoctorAvailable',
    component: UserAccountComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'inlineAppointment',
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