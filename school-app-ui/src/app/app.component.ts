import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { ActivatedRoute, NavigationEnd, NavigationStart, Router } from '@angular/router';
import { filter, map } from 'rxjs/operators';
import { UserService } from './user.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {

  constructor(private userService: UserService, private router: Router, private activatedRoute: ActivatedRoute, private titleService: Title) {
  }

  ngOnInit() {
    const appTitle = this.titleService.getTitle();
    this.router
      .events.pipe(
        filter(event => event instanceof NavigationEnd),
        map(() => {
          const child = this.activatedRoute.firstChild;
          if (child != null && child.snapshot.data['title']) {
            return child.snapshot.data['title'];
          }
          return appTitle;
        })
      ).subscribe((ttl: string) => this.titleService.setTitle(ttl));
    this.router
      .events.pipe(
        filter(event => event instanceof NavigationEnd)).subscribe(e => {
          let event = e as NavigationEnd;
          if (!this.isAuthenticated() && event.url != '/login') {
            this.router.navigate(['/login']);
          } else if (event.url == '/logout') {
            this.logout();
          }
        });
    this.router
      .events.pipe(
        filter(event => event instanceof NavigationStart)).subscribe(e => {
          let event = e as NavigationStart;
          if (event.url == '/logout') {
            this.logout();
          }
        });
  }

  isAuthenticated(): boolean {
    return this.userService.isAuthenticated();
  }

  logout() {
    this.userService.clearAuthentication();
    this.router.navigate(['/login']);
  }

}
