import {Component, OnInit} from '@angular/core';
import {LoginService} from '../login.service';
import {FormControl} from '@angular/forms';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loggedIn: boolean;
  username: string;
  password: string;
  userName: FormControl = new FormControl();

  constructor(private loginService: LoginService) {
    this.username = "";
    this.password = "";
    if (localStorage.getItem('PortalAdminHasLoggedIn') == '' || localStorage.getItem('PortalAdminHasLoggedIn') == null) {
      this.loggedIn = false;
    } else {
      this.loggedIn = true;
    }
  }

  onSubmit() {
    this.loginService.sendCredential(this.username, this.password).subscribe(
      res => {
        this.loggedIn = true;
        localStorage.setItem('PortalAdminHasLoggedIn', 'true');
        localStorage.setItem("username", this.username)
        //console.log(res);
        //location.reload();
      },
      err => console.log(err)
    );
  }

  ngOnInit(): void {
  }

}
