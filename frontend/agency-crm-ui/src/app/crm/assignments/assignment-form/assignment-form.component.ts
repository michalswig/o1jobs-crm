import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { AssignmentService } from '../services/assignment.service';
import { ClientService } from '../../services/client.service';
import { Client } from '../../models/client.model';
import { CareRecipientService } from '../../care-recipients/services/care-recipient.service';
import { CaregiverService } from '../../services/caregiver.service';
import { Caregiver } from '../../caregivers/models/caregiver.model';
import { LanguageLevel, AccommodationType } from '../../../shared/models/domain-enums.model';
import { EnumLabelPipe } from '../../../shared/pipes/enum-label.pipe';
import {CareRecipient} from '../../care-recipients/models/care-recipient';
import {Assignment} from '../models/assignment';

@Component({
  selector: 'app-assignment-form',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    RouterLink,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatSelectModule,
    MatCheckboxModule,
    EnumLabelPipe
  ],
  templateUrl: './assignment-form.component.html',
  styleUrl: './assignment-form.component.scss'
})
export class AssignmentFormComponent implements OnInit {

  assignmentId: number | null = null;
  isEditMode = false;

  languageLevels: LanguageLevel[] = ['NONE', 'BASIC', 'COMMUNICATIVE', 'GOOD', 'VERY_GOOD'];
  accommodationTypes: AccommodationType[] = ['HOUSE', 'APARTMENT'];

  clients: Client[] = [];
  careRecipients: CareRecipient[] = [];
  caregivers: Caregiver[] = [];

  form: FormGroup;

  constructor(
    private readonly fb: FormBuilder,
    private readonly route: ActivatedRoute,
    private readonly router: Router,
    private readonly assignmentService: AssignmentService,
    private readonly clientService: ClientService,
    private readonly careRecipientService: CareRecipientService,
    private readonly caregiverService: CaregiverService
  ) {
    this.form = this.fb.group({
      clientId: ['', Validators.required],
      careRecipientId: ['', Validators.required],
      startDate: ['', Validators.required],
      city: ['', Validators.required],
      streetAddress: [''],
      salaryMonthlyNet: ['', Validators.required],
      languageLevel: ['', Validators.required],
      requirements: [''],
      caregiverId: [''],
      accommodationType: ['', Validators.required],
      ownBathroom: [false],
      ownRoom: [false]
    });
  }

  ngOnInit(): void {
    this.loadClients();
    this.loadCareRecipients();
    this.loadCaregivers();

    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam) {
      this.isEditMode = true;
      this.assignmentId = Number(idParam);
      this.loadAssignment(this.assignmentId);
    }
  }

  loadClients(): void {
    this.clientService.getAll(0, 100).subscribe({
      next: (page) => this.clients = page.content,
      error: (err) => console.error('Error loading clients:', err)
    });
  }

  loadCareRecipients(): void {
    this.careRecipientService.getAll(0, 100).subscribe({
      next: (page) => this.careRecipients = page.content,
      error: (err) => console.error('Error loading care recipients:', err)
    });
  }

  loadCaregivers(): void {
    this.caregiverService.getAll(0, 100).subscribe({
      next: (page) => this.caregivers = page.content,
      error: (err) => console.error('Error loading caregivers:', err)
    });
  }

  loadAssignment(id: number): void {
    this.assignmentService.getById(id).subscribe({
      next: (assignment: Assignment) => {
        this.form.patchValue(assignment);
      },
      error: (err) => console.error('Error loading assignment:', err)
    });
  }

  onSubmit(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const formValue = this.form.value;

    if (this.isEditMode && this.assignmentId) {
      this.assignmentService.update(this.assignmentId, formValue).subscribe({
        next: () => this.router.navigate(['/assignments']),
        error: (err) => console.error('Error updating assignment:', err)
      });
    } else {
      this.assignmentService.create(formValue).subscribe({
        next: () => this.router.navigate(['/assignments']),
        error: (err) => console.error('Error creating assignment:', err)
      });
    }
  }
}
