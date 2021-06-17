import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http'
import {Observable}     from 'rxjs';

@Injectable()
export class LoginService {

  constructor (private http: HttpClient) {}

  isUserLoggedIn() {
    return true;
  }

  sendCredential(username: string, password: string) {
    let url = "http://localhost:8015/index";
    let params = 'username='+username+'&password='+password;
    let headers = new HttpHeaders(
    {
      'Content-Type': 'application/x-www-form-urlencoded'
      // 'Access-Control-Allow-Credentials' : true
    });
    return this.http.post(url, params, {headers: headers, withCredentials : true, responseType: 'text'});
  }

  logout() {
     //localStorage.setItem('PortalAdminHasLoggedIn', '')
     let url = "http://localhost:8015/logout";
     return this.http.get(url, { withCredentials: true,responseType: 'text' });
   }

}
