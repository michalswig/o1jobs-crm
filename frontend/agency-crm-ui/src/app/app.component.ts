import {Component} from '@angular/core';
import {LoginComponent} from './identity/login/login.component';
import {RouterOutlet} from '@angular/router';

@Component({
  selector: 'app-root',
  imports: [LoginComponent, RouterOutlet],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'agency-crm-ui';
}
