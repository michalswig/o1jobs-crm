import {Routes} from '@angular/router';
import {LoginComponent} from './identity/login/login.component';
import {DashboardComponent} from './dashboard/dashboard.component';
import {authGuard} from './core/guards/auth.guard';
import {ClientListComponent} from './crm/clients/client-list/client-list.component';

export const routes: Routes = [
  {path: '', redirectTo: 'login', pathMatch: 'full'},
  {path: 'login', component: LoginComponent},
  {path: 'dashboard', component: DashboardComponent, canActivate: [authGuard]},
  {path: 'clients', component: ClientListComponent, canActivate: [authGuard]}
];
