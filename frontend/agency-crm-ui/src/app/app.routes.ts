import {Routes} from '@angular/router';
import {LoginComponent} from './identity/login/login.component';
import {DashboardComponent} from './dashboard/dashboard.component';
import {authGuard} from './core/guards/auth.guard';
import {ClientListComponent} from './crm/clients/client-list/client-list.component';
import {AppLayoutComponent} from './core/layout/app-layout/app-layout.component';
import {ClientFormComponent} from './crm/clients/client-form/client-form.component';
import {CaregiverFormComponent} from './crm/caregivers/caregiver-form/caregiver-form.component';
import {CaregiverListComponent} from './crm/caregivers/caregiver-list/caregiver-list.component';
import {CareRecipientFormComponent} from './crm/care-recipients/care-recipient-form/care-recipient-form.component';
import {CareRecipientListComponent} from './crm/care-recipients/care-recipient-list/care-recipient-list.component';
import {AssignmentListComponent} from './crm/assignments/assignment-list/assignment-list.component';
import {AssignmentFormComponent} from './crm/assignments/assignment-form/assignment-form.component';
import {AssignmentDetailComponent} from './crm/assignments/assignment-detail/assignment-detail.component';
import {IntermediaryListComponent} from './crm/intermediaries/intermediary-list/intermediary-list.component';
import {IntermediaryFormComponent} from './crm/intermediaries/intermediary-form/intermediary-form.component';
import {HelpComponent} from './help/help.component';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  {
    path: '',
    component: AppLayoutComponent,
    canActivate: [authGuard],
    children: [
      { path: 'clients', component: ClientListComponent },
      { path: 'dashboard', component: DashboardComponent },
      { path: 'help', component: HelpComponent },
      { path: 'clients/new', component: ClientFormComponent },
      { path: 'clients/edit/:id', component: ClientFormComponent },
      { path: 'caregivers', component: CaregiverListComponent },
      { path: 'caregivers/new', component: CaregiverFormComponent },
      { path: 'caregivers/edit/:id', component: CaregiverFormComponent },
      { path: 'care-recipients', component: CareRecipientListComponent },
      { path: 'care-recipients/new', component: CareRecipientFormComponent },
      { path: 'care-recipients/edit/:id', component: CareRecipientFormComponent },
      { path: 'assignments', component: AssignmentListComponent },
      { path: 'assignments/new', component: AssignmentFormComponent },
      { path: 'assignments/edit/:id', component: AssignmentFormComponent },
      { path: 'assignments/:id', component: AssignmentDetailComponent },
      { path: 'intermediaries', component: IntermediaryListComponent },
      { path: 'intermediaries/new', component: IntermediaryFormComponent },
      { path: 'intermediaries/edit/:id', component: IntermediaryFormComponent },
    ]
  }
];
