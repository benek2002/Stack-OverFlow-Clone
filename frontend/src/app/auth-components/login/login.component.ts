import { Component } from '@angular/core';
import { AuthService } from '../../auth-services/auth-service/auth.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, defaultUrlMatcher } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { error } from 'console';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  loginForm!: FormGroup;


  constructor(private authService: AuthService, 
              private fb: FormBuilder,
              private router: Router,
              private snackBar: MatSnackBar) {}

  ngOnInit(): void{
    this.loginForm = this.fb.group({
      email: ['', Validators.required],
      password:['', Validators.required],
    });
  }

  login(): void{
    console.log(this.loginForm.value);
    this.authService.login(this.loginForm.get(['email'])!.value, this.loginForm.get(['password'])!.value).subscribe(() => {
        this.router.navigateByUrl("/user/dashboard");
      },
      (        error: any) => {
        console.log(error)
        this.snackBar.open("Bad credentials", "Close", {duration: 5000, 
        panelClass: 'error-snackbar'});
      }
    )
    }

    
  }

