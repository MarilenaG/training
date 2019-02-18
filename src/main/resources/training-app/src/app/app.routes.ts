import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { SignupComponent } from './signup/signup.component';
import { ConfirmRegistrationComponent } from './confirm-registration/confirm-registration.component';
import { LoginComponent } from './login/login.component';

const mainRoutes: Routes = [
  { path: 'signup',
    component: SignupComponent,
  }  ,
  { path: 'confirmRegistration',
    component: ConfirmRegistrationComponent,
  }  ,
  { path: 'login',
    component: LoginComponent,
  } 
];


@NgModule({
  declarations: [],
  imports: [
    RouterModule.forRoot(mainRoutes)
  ],

  exports: [
    RouterModule
  ]
})

export class AppRoutesModule { }