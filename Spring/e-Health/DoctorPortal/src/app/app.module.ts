import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';
import {routing} from './app-routing.module';

import {AppComponent} from './app.component';
import {NavbarComponent} from './navbar/navbar.component';
import {LoginComponent} from './login/login.component';

import {LoginService} from './login.service';
import {CreateAppointmentComponent} from './create-appointment/create-appointment.component';
import {UserService} from './doctor.service'
import {DataTablesModule} from "angular-datatables";
import {InlineAppointmentComponent} from './inline-appointment/inline-appointment.component';
import {NgbAlertModule, NgbModule, NgbPaginationModule} from '@ng-bootstrap/ng-bootstrap';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';

import {MatSliderModule} from '@angular/material/slider';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {MatButtonModule} from '@angular/material/button';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatRippleModule} from '@angular/material/core';
import {MatInputModule} from '@angular/material/input';
import {AuthGuard} from './auth.guard';

const modules = [
  MatButtonModule,
  MatFormFieldModule,
  MatInputModule,
  MatRippleModule,
  MatSliderModule,
  MatDatepickerModule
];

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    LoginComponent,
    CreateAppointmentComponent,
    InlineAppointmentComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    DataTablesModule,
    NgbPaginationModule,
    NgbAlertModule,
    NgbModule,
    ReactiveFormsModule,
    //modules,
    //MdbModule,
    routing,
    BrowserAnimationsModule
  ],
  exports: [
    //modules
  ],
  providers: [
    LoginService,
    AuthGuard,
    UserService
  ],
  bootstrap: [AppComponent],
  //entryComponents: [ ModalContentComponent ]
})
export class AppModule {
}
