import { ChangeDetectionStrategy } from '@angular/core';
import { Component, OnInit } from '@angular/core';
import { NbMenuItem } from '@nebular/theme';

@Component({
  selector: 'app-menu',
  changeDetection: ChangeDetectionStrategy.OnPush,
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.scss']
})
export class MenuComponent implements OnInit {

  items: NbMenuItem[] = [
    {title: 'Home', link: '/home'},
    {title: 'Courses', link: '/course'},
    {title: 'Students', link: '/student'},
    {title: 'Logout', link: '/logout'}
  ];  

  constructor() { }

  ngOnInit(): void {
  }

}
