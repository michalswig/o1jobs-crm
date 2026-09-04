import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatIconModule } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card';
import { HttpErrorResponse } from '@angular/common/http';
import { CaregiverService } from '../../services/caregiver.service';
import { Caregiver } from '../models/caregiver.model';
import { CareCapability } from '../../../shared/models/domain-enums.model';
import { EnumLabelPipe } from '../../../shared/pipes/enum-label.pipe';
import { CaregiverAvatarComponent } from '../caregiver-avatar/caregiver-avatar.component';

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
    MatCheckboxModule,
    MatIconModule,
    MatCardModule,
    EnumLabelPipe,
    CaregiverAvatarComponent
  ],
  templateUrl: './caregiver-form.component.html',
  styleUrl: './caregiver-form.component.scss'
})
export class CaregiverFormComponent implements OnInit {

  caregiverId: number | null = null;
  isEditMode = false;

  hasPhoto = false;
  selectedPhotoFile: File | null = null;
  uploadingPhoto = false;
  photoError: string | null = null;

  genders = ['MALE', 'FEMALE'];
  nationalities = ['POLISH', 'GEORGIAN', 'MOLDOVAN', 'RUSSIAN', 'UKRAINIAN'];
  dementiaLevels = ['NONE', 'MILD', 'ADVANCED'];
  capabilities: CareCapability[] = [
    'TRANSFER', 'TRANSFER_WITH_LIFT', 'CATHETER', 'STOMA', 'DIAPERS', 'PEG',
    'OXYGEN', 'INSULIN', 'INJECTION', 'ALZHEIMER', 'DEMENTIA', 'PARKINSON',
    'MS', 'COOKING', 'CLEANING', 'SHOPPING', 'NIGHT_CARE'
  ];

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
      recruiterNotes: [''],
      dementiaExperience: [''],
      capabilities: [[]]
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
        this.hasPhoto = caregiver.hasPhoto;
      },
      error: (err) => console.error('Error loading caregiver:', err)
    });
  }

  onPhotoSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    this.selectedPhotoFile = input.files?.length ? input.files[0] : null;
    this.photoError = null;
  }

  onUploadPhoto(): void {
    if (!this.selectedPhotoFile || !this.caregiverId) {
      return;
    }
    if (this.selectedPhotoFile.type !== 'image/jpeg') {
      this.photoError = 'Es sind nur JPG-Dateien zulässig.';
      return;
    }

    this.uploadingPhoto = true;
    this.caregiverService.uploadPhoto(this.caregiverId, this.selectedPhotoFile).subscribe({
      next: () => {
        this.hasPhoto = true;
        this.selectedPhotoFile = null;
        this.uploadingPhoto = false;
      },
      error: (err: HttpErrorResponse) => {
        this.photoError = err.status === 400
          ? 'Das hochgeladene Foto ist ungültig.'
          : 'Foto konnte nicht hochgeladen werden. Bitte versuchen Sie es erneut.';
        this.uploadingPhoto = false;
      }
    });
  }

  onDeletePhoto(): void {
    if (!this.caregiverId || !confirm('Möchten Sie das Foto wirklich löschen?')) {
      return;
    }
    this.caregiverService.deletePhoto(this.caregiverId).subscribe({
      next: () => this.hasPhoto = false,
      error: (err) => console.error('Error deleting photo:', err)
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
