import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
//import { routing }  from './app.routing.ts.bak';
import { routing }  from './app-routing.module';

import { AppComponent } from './app.component';
import { NavbarComponent } from './navbar/navbar.component';
import { LoginComponent } from './login/login.component';

import { LoginService } from './login.service';
import { UserAccountComponent } from './user-account/user-account.component';
import {UserService} from './patient.service'
import { DataTablesModule } from "angular-datatables";
import { SaveExportComponent } from './save-export/save-export.component';
import {NgbPaginationModule, NgbAlertModule, NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
//import { MdbModule } from 'mdb-angular-ui-kit';
// import '~@angular/cdk/overlay-prebuilt.css';
// //  - @angular/cdk/overlay
//  - @angular/cdk/portal
//  - @angular/cdk/observers
//  - @angular/cdk/a11y
//  - @angular/cdk/layout
import { MatSliderModule } from '@angular/material/slider';
import {MatDatepickerModule} from '@angular/material/datepicker';
import { MatButtonModule } from '@angular/material/button'; 
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatRippleModule } from '@angular/material/core';
import { MatCommonModule } from '@angular/material/core';
// import {MatInputModule} from '@angular/material/core';
import {MatInputModule} from '@angular/material/input';
import { ReactiveFormsModule } from '@angular/forms';
import { AuthGuard } from './auth.guard';

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
    UserAccountComponent,
    SaveExportComponent
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
export class AppModule { }
