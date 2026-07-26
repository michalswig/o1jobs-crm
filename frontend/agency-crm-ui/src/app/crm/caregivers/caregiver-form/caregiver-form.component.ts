import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { CaregiverService } from '../../services/caregiver.service';
import {Caregiver} from '../models/caregiver.model';

@Component({
  selector: 'app-caregiver-form',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    RouterLink,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatSelectModule,
    MatDatepickerModule,
    MatCheckboxModule
  ],
  templateUrl: './caregiver-form.component.html',
  styleUrl: './caregiver-form.component.scss'
})
export class CaregiverFormComponent implements OnInit {

  caregiverId: number | null = null;
  isEditMode = false;

  genders = ['MALE', 'FEMALE'];
  nationalities = ['POLISH', 'GEORGIAN', 'MOLDOVAN', 'RUSSIAN', 'UKRAINIAN'];

  form: FormGroup;

  constructor(
    private readonly fb: FormBuilder,
    private readonly route: ActivatedRoute,
    private readonly router: Router,
    private readonly caregiverService: CaregiverService
  ) {
    this.form = this.fb.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      gender: ['', Validators.required],
      birthDate: ['', Validators.required],
      weightKg: ['', Validators.required],
      heightCm: ['', Validators.required],
      phone: ['', Validators.required],
      email: [''],
      nationality: ['', Validators.required],
      careerStartDate: ['', Validators.required],
      hasDriverLicense: [false],
      smoker: [false],
      medicalQualificationNotes: [''],
      recruiterNotes: ['']
    });
  }

  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('id');

    if (idParam) {
      this.isEditMode = true;
      this.caregiverId = Number(idParam);
      this.loadCaregiver(this.caregiverId);
    }
  }

  loadCaregiver(id: number): void {
    this.caregiverService.getById(id).subscribe({
      next: (caregiver: Caregiver) => {
        this.form.patchValue(caregiver);
      },
      error: (err) => console.error('Error loading caregiver:', err)
    });
  }

  onSubmit(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const formValue = this.form.value;

    if (this.isEditMode && this.caregiverId) {
      this.caregiverService.update(this.caregiverId, formValue).subscribe({
        next: () => this.router.navigate(['/caregivers']),
        error: (err) => console.error('Error updating caregiver:', err)
      });
    } else {
      this.caregiverService.create(formValue).subscribe({
        next: () => this.router.navigate(['/caregivers']),
        error: (err) => console.error('Error creating caregiver:', err)
      });
    }
  }
}
