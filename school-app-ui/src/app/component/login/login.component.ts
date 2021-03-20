import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from 'src/app/user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;

  isError: boolean;

  submitted: boolean;

  constructor(private userService: UserService, private router: Router, private formBuilder: FormBuilder) {
    this.loginForm = this.formBuilder.group({
      username: [null, [Validators.required]],
      password: [null, [Validators.required]]
    });
    this.isError = false;
    this.submitted = false;
  }

  ngOnInit(): void {
  }

  get loginFormControl() {
    return this.loginForm.controls;
  }

  login() {
    this.submitted = true;
    if (this.loginForm.valid) {
      this.userService.setUserAndPasssword(this.loginForm.get('username')!.value, this.loginForm.get('password')!.value);
      this.userService.checkUser().subscribe(e => {
        this.userService.storeAuthentication();
        this.router.navigate(['/home']);
      }, error => this.handleError(error));
    }
  }

  handleError(error: any) {
    console.log(error);
    this.isError = true;
  }

}
