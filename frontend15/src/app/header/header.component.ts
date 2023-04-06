import {Component, OnInit} from '@angular/core';
import {OidcSecurityService} from "angular-auth-oidc-client";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  isAuthenticated: boolean = false;

  constructor(private oidcSecurityService: OidcSecurityService) {

  }
  ngOnInit(): void {
    this.oidcSecurityService.checkAuth()
      .subscribe(({isAuthenticated}) => {
        this.isAuthenticated = isAuthenticated;
        /*...*/
      });
  }
  login() {
    this.oidcSecurityService.authorize();
  }
  logout() {
    this.oidcSecurityService.logoff().subscribe((result) => console.log(result));
  }
  logOff() {
    this.oidcSecurityService.logoffAndRevokeTokens();
  }
}
