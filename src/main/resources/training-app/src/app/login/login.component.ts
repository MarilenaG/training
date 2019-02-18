import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormControl, FormGroup} from '@angular/forms';
import {Router} from '@angular/router';
import { first } from 'rxjs/operators';
import { AlertService } from '../services/alert.service';
import { UserService } from '../services/user.service';
import { TokenStorageService} from '../services/tokenStorageService';
import { Validators } from '@angular/forms'
import { from } from 'rxjs/observable/from';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
private loginForm: FormGroup;

  constructor(private formBuilder: FormBuilder,
    private router: Router , private userService: UserService, private alertService: AlertService,
    private tokenStorageService: TokenStorageService ) { }

    ngOnInit() {
      this.loginForm = this.formBuilder.group({
        userName:  new FormControl('', [Validators.required]),
        password: new FormControl('', [Validators.required, Validators.minLength(6)])
      });
     }

onClickSubmit(data) {
  // stop here if form is invalid
  console.log('form: ', this.loginForm);
  if (this.loginForm.invalid) {
    this.alertService.error('please provide username and password');
    return;
  }
  this.userService.login(this.loginForm.value)
  .pipe(first())
  .subscribe(
      data => {
          console.log(data);
          this.alertService.success('Confirmed!');
          this.tokenStorageService.addToken(data);
      },
      error => {
         console.error(error);
         this.alertService.error(error.message);
      });
 }
}

