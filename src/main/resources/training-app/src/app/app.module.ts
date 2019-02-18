import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule , ReactiveFormsModule} from '@angular/forms';
import { HttpModule } from '@angular/http';

import { AlertService } from './services/alert.service';
import { UserService } from './services/user.service';
import { AppRoutesModule } from './app.routes'
import { TokenStorageService } from './services/tokenStorageService';

import { AppComponent } from './app.component';
import { AlertComponent } from './alert/alert.component';
import { SignupComponent } from './signup/signup.component';
import { ConfirmRegistrationComponent } from './confirm-registration/confirm-registration.component';
import { LoginComponent } from './login/login.component';

@NgModule({
  declarations: [
    AppComponent,
    AlertComponent,
    SignupComponent,
    ConfirmRegistrationComponent,
    LoginComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    AppRoutesModule,
    ReactiveFormsModule
  ],
  
  providers: [AlertService,
              UserService,
              TokenStorageService],
  bootstrap: [AppComponent]
})
export class AppModule { }






