import {Routes} from '@angular/router';
import {LoginComponent} from './identity/login/login.component';
import {DashboardComponent} from './dashboard/dashboard.component';
import {authGuard} from './core/guards/auth.guard';
import {ClientListComponent} from './crm/clients/client-list/client-list.component';
import {AppLayoutComponent} from './core/layout/app-layout/app-layout.component';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  {
    path: '',
    component: AppLayoutComponent,
    canActivate: [authGuard],
    children: [
      { path: 'clients', component: ClientListComponent },
      { path: 'dashboard', component: DashboardComponent },
    ]
  }
];
