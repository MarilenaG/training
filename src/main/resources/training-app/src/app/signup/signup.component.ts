import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
import { first } from 'rxjs/operators';
import { AlertService } from '../services/alert.service';
import { UserService } from '../services/user.service';


@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {
  title = 'Signup to the mentors application';
  registerForm: FormGroup;
  submitted = false;

  constructor(private formBuilder: FormBuilder,
    private router: Router , private userService: UserService, private alertService: AlertService
    ) { }

  ngOnInit() {
    this.registerForm = this.formBuilder.group({
      firstName:  new FormControl('', [Validators.required]),
      lastName:  new FormControl('', [Validators.required]),
      userName:  new FormControl('', [Validators.required]),
      contact:   new FormControl(''),
      password: new FormControl('', [Validators.required, Validators.minLength(6)])

  });
   }
   onClickSubmit(data) {
    // stop here if form is invalid
    console.log('form: ', this.registerForm);
    if (this.registerForm.invalid) {
      this.alertService.error('please provide needen infos');
      return;
    }
    this.userService.register(this.registerForm.value)
    .pipe(first())
    .subscribe(
        data => {
            this.alertService.success('Registered. Please check your email and use the registration code in the next screen.');
            this.router.navigate(['/confirmRegistration']);
        },
        error => {
           console.error(error);
           this.alertService.error(error.message);
        });
   }
  }



