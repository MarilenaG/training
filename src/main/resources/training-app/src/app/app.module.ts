import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { RouterModule} from '@angular/router';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';

import { AppComponent } from './app.component';
import { SignupComponent } from './signup/signup.component';
import { AlertComponent } from './alert/alert.component';
import { AlertService } from './services/alert.service';
import { UserService } from './services/user.service';
import { User } from './model/user';

@NgModule({
  declarations: [
    AppComponent,
    SignupComponent,
    AlertComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule
  ],
  providers: [AlertService,
              UserService],
  bootstrap: [AppComponent]
})
export class AppModule { }






