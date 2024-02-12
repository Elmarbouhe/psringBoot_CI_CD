import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import { AuthService } from '../services/auth.service';
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loginForm!: FormGroup;
  constructor(private fb : FormBuilder,private authService:AuthService,private roter:Router) { }

  ngOnInit(): void {
    this.loginForm = this.fb.group({
      username : this.fb.control(""),
      password : this.fb.control("")
    })
  }
  login() {
  let username = this.loginForm.value.username;
  let password = this.loginForm.value.password;
  this.authService.login(username,password).subscribe({
    next : data=> {
      this.authService.loadProfile(data);
      this.roter.navigateByUrl("/admin");

    },
    error : err => {
      console.log(err);
    }
  })
    }

  }