import { Component, OnInit, ViewEncapsulation  } from '@angular/core';
import {User} from '../../../model/User';
import {LoginService} from '../../../service/login.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  encapsulation: ViewEncapsulation.None,
  providers: [LoginService]
})
export class LoginComponent implements OnInit {

  model: User = new User();

  constructor(private loginService: LoginService) { }

  ngOnInit() {
    this.loginService.getUser();
  }

  onSubmit(){
    this.loginService.login(this.model).subscribe((r) => {
      console.log(r);
      this.loginService.getUser();
    })
    // this.loginService.authenticate({"username":this.model.email, "password":this.model.password}, function(){
    //   console.log("succuess login")
    // });
  }

}
