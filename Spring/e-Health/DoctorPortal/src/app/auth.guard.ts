import {Injectable} from '@angular/core';
import {CanActivate} from '@angular/router';
import {LoginService} from './login.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  constructor(private authService: LoginService) {

  }

  // canActivate(
  //   route: ActivatedRouteSnapshot,
  //   state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
  canActivate() {
    //logic
    // return true;
    if (this.authService.isUserLoggedIn()) {
      return true;
    } else {
      window.alert('Permission denied for this page');
      return false;
    }
  }

}
