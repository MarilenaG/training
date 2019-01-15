import { Injectable } from '@angular/core';
import { User } from '../model/user';
import { Http } from '@angular/http';
import { Observable } from 'rxjs';


@Injectable()
export class UserService {
  constructor(private http: Http) { }
  register(user: User): Observable<any> {
    return this.http.post('api/registration', user);
  }
}