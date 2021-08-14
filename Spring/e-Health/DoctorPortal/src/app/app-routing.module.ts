import {ModuleWithProviders} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from './login/login.component'
import {CreateAppointmentComponent} from './create-appointment/create-appointment.component';
import {InlineAppointmentComponent} from './inline-appointment/inline-appointment.component';
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
    path: 'datesDoctorAvailable',
    component: CreateAppointmentComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'inlineAppointment',
    component: InlineAppointmentComponent,
    canActivate: [AuthGuard]
  },
];

// @NgModule({
//   imports: [RouterModule.forRoot(routes),CommonModule],
//   exports: [RouterModule]
// })


//export class AppRoutingModule { }
export const routing: ModuleWithProviders<RouterModule> = RouterModule.forRoot(routes, {onSameUrlNavigation: 'reload'});
