import {Routes} from '@angular/router';
import {LoginComponent} from './identity/login/login.component';
import {DashboardComponent} from './dashboard/dashboard.component';
import {authGuard} from './core/guards/auth.guard';
import {ClientListComponent} from './crm/clients/client-list/client-list.component';
import {AppLayoutComponent} from './core/layout/app-layout/app-layout.component';
import {ClientFormComponent} from './crm/clients/client-form/client-form.component';
import {CaregiverFormComponent} from './crm/caregivers/caregiver-form/caregiver-form.component';
import {CaregiverListComponent} from './crm/caregivers/caregiver-list/caregiver-list.component';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  {
    path: '',
    component: AppLayoutComponent,
    canActivate: [authGuard],
    children: [
      { path: 'clients', component: ClientListComponent },
      { path: 'dashboard', component: DashboardComponent },
      { path: 'clients/new', component: ClientFormComponent },
      { path: 'clients/edit/:id', component: ClientFormComponent },
      { path: 'caregivers', component: CaregiverListComponent },
      { path: 'caregivers/new', component: CaregiverFormComponent },
      { path: 'caregivers/edit/:id', component: CaregiverFormComponent },
    ]
  }
];
