import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { CareRecipientService } from '../services/care-recipient.service';
import { ClientService } from '../../services/client.service';
import { Client } from '../../models/client.model';
import { CareCapability } from '../../../shared/models/domain-enums.model';
import { EnumLabelPipe } from '../../../shared/pipes/enum-label.pipe';
import {CareRecipient} from '../models/care-recipient';

@Component({
  selector: 'app-care-recipient-form',
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
  templateUrl: './care-recipient-form.component.html',
  styleUrl: './care-recipient-form.component.scss'
})
export class CareRecipientFormComponent implements OnInit {

  careRecipientId: number | null = null;
  isEditMode = false;

  genders = ['MALE', 'FEMALE'];
  mobilityLevels = ['FULL', 'CANE', 'ROLLATOR', 'WHEELCHAIR', 'BEDRIDDEN'];
  dementiaLevels = ['NONE', 'MILD', 'ADVANCED'];
  capabilities: CareCapability[] = [
    'TRANSFER', 'TRANSFER_WITH_LIFT', 'CATHETER', 'STOMA', 'DIAPERS', 'PEG',
    'OXYGEN', 'INSULIN', 'INJECTION', 'ALZHEIMER', 'DEMENTIA', 'PARKINSON',
    'MS', 'COOKING', 'CLEANING', 'SHOPPING', 'NIGHT_CARE'
  ];

  clients: Client[] = [];

  form: FormGroup;

  constructor(
    private readonly fb: FormBuilder,
    private readonly route: ActivatedRoute,
    private readonly router: Router,
    private readonly careRecipientService: CareRecipientService,
    private readonly clientService: ClientService
  ) {
    this.form = this.fb.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      dateOfBirth: ['', Validators.required],
      heightCm: ['', Validators.required],
      weightKg: ['', Validators.required],
      gender: ['', Validators.required],
      mobilityLevel: ['', Validators.required],
      dementiaLevel: ['', Validators.required],
      diseasesNotes: [''],
      smoker: [false],
      hasPets: [false],
      petsNotes: [''],
      liftingAidsNotes: [''],
      medicalNotes: [''],
      requiredCapabilities: [[]],
      clientId: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    this.loadClients();

    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam) {
      this.isEditMode = true;
      this.careRecipientId = Number(idParam);
      this.loadCareRecipient(this.careRecipientId);
    }
  }

  loadClients(): void {
    this.clientService.getAll(0, 100).subscribe({
      next: (page) => this.clients = page.content,
      error: (err) => console.error('Error loading clients:', err)
    });
  }

  loadCareRecipient(id: number): void {
    this.careRecipientService.getById(id).subscribe({
      next: (careRecipient: CareRecipient) => {
        this.form.patchValue(careRecipient);
      },
      error: (err) => console.error('Error loading care recipient:', err)
    });
  }

  onSubmit(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const formValue = this.form.value;

    if (this.isEditMode && this.careRecipientId) {
      this.careRecipientService.update(this.careRecipientId, formValue).subscribe({
        next: () => this.router.navigate(['/care-recipients']),
        error: (err) => console.error('Error updating care recipient:', err)
      });
    } else {
      this.careRecipientService.create(formValue).subscribe({
        next: () => this.router.navigate(['/care-recipients']),
        error: (err) => console.error('Error creating care recipient:', err)
      });
    }
  }
}
