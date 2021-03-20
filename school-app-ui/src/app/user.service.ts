import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private cookieName: string = 'school-app-auth-cookie'

  private url: string = environment.apiUrl + "/user";

  private basic: string | null;

  private user: string | null;

  private authenticated: boolean;

  constructor(private http: HttpClient) {
    this.basic = this.getCookie(this.cookieName);
    this.authenticated = this.basic != null;
    this.user = this.basic != null ? atob(this.basic).split(':')[0] : null;
  }

  isAuthenticated() {
    return this.authenticated;
  }

  setUserAndPasssword(user: string, password: string) {
    this.basic = btoa(user + ':' + password);
    this.user = user;
  }

  getBasic() {
    return this.basic;
  }

  getUser() {
    return this.user;
  }

  checkUser() {
    return this.http.post<string>(this.url, null, { headers: this.getHttpHeaders(), responseType: 'text' as 'json' });
  }

  storeAuthentication() {
    this.setCookie(this.cookieName, this.basic!, 1);
    this.authenticated = true;
  }

  clearAuthentication() {
    this.eraseCookie(this.cookieName);
    this.basic = null;
    this.authenticated = false;
  }

  private setCookie(name: string, value: string, days: number) {
    var expires = "";
    if (days) {
      var date = new Date();
      date.setTime(date.getTime() + (days * 24 * 60 * 60 * 1000));
      expires = "; expires=" + date.toUTCString();
    }
    document.cookie = name + "=" + (value || "") + expires + "; path=/";
  }

  private getCookie(name: string) {
    var nameEQ = name + "=";
    var ca = document.cookie.split(';');
    for (var i = 0; i < ca.length; i++) {
      var c = ca[i];
      while (c.charAt(0) == ' ') c = c.substring(1, c.length);
      if (c.indexOf(nameEQ) == 0) return c.substring(nameEQ.length, c.length);
    }
    return null;
  }
  
  private eraseCookie(name: string) {
    document.cookie = name + '=; Path=/; Expires=Thu, 01 Jan 1970 00:00:01 GMT;';
  }

  private getHttpHeaders(): HttpHeaders {
    return new HttpHeaders()
      .set("Authorization", "Basic " + this.getBasic());
  }

}
