import { Injectable } from '@angular/core';
import { User } from '../model/user';
import { ApplicationUser } from '../model/applicationUser';
import { Http , HttpModule} from '@angular/http';
import { Observable } from 'rxjs';
import { Router,NavigationExtras } from '@angular/router';


@Injectable()
export class UserService {
  constructor(private http: Http, private router : Router) { }
  
  register(user: User): Observable<any> {
    return this.http.post('training/signup', user);
  }

  confirmRegistration(user: User): Observable<any> {
        return this.http.put('training/confirmRegistration?userName='+ user.userName+'&registrationCode='+ user.registrationCode,null);
  }

  login(applicationUser: ApplicationUser): Observable<any> {
    return this.http.put('training/login', applicationUser);
  }
}