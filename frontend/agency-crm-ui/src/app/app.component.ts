import {Component} from '@angular/core';
import {LoginComponent} from './identity/login/login.component';

@Component({
  selector: 'app-root',
  imports: [LoginComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'agency-crm-ui';
}
