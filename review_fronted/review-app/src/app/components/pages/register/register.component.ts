import { Component, OnInit } from '@angular/core';
import {User} from '../../../model/User';
import {LoginService} from '../../../service/login.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
  providers: [LoginService]
})
export class RegisterComponent implements OnInit {

  constructor(private loginService: LoginService) { }

  ngOnInit() {
  }

  model = new User();

  onSubmit(){
  	console.log(this);
  	this.loginService.registerUser(this.model).subscribe((response) => {
  		console.log(response);
  	})
  }

}
