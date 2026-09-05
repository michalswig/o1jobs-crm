import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';
import { MatCardModule } from '@angular/material/card';
import { IntermediaryService } from '../services/intermediary.service';
import { EnumLabelPipe } from '../../../shared/pipes/enum-label.pipe';
import {Intermediary, IntermediaryType} from '../models/intermediary.interface';

@Component({
  selector: 'app-intermediary-form',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    RouterLink,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatSelectModule,
    MatCardModule,
    EnumLabelPipe
  ],
  templateUrl: './intermediary-form.component.html',
  styleUrl: './intermediary-form.component.scss'
})
export class IntermediaryFormComponent implements OnInit {

  intermediaryId: number | null = null;
  isEditMode = false;

  intermediaryTypes: IntermediaryType[] = ['PARTNER', 'CAREGIVER', 'EMPLOYEE', 'OTHER'];

  form: FormGroup;

  constructor(
    private readonly fb: FormBuilder,
    private readonly route: ActivatedRoute,
    private readonly router: Router,
    private readonly intermediaryService: IntermediaryService
  ) {
    this.form = this.fb.group({
      intermediaryType: ['', Validators.required],
      name: ['', Validators.required],
      country: [''],
      city: [''],
      postalCode: [''],
      streetAddress: [''],
      email: ['', Validators.required],
      phone: ['', Validators.required],
      notes: ['']
    });
  }

  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('id');

    if (idParam) {
      this.isEditMode = true;
      this.intermediaryId = Number(idParam);
      this.loadIntermediary(this.intermediaryId);
    }
  }

  loadIntermediary(id: number): void {
    this.intermediaryService.getById(id).subscribe({
      next: (intermediary: Intermediary) => this.form.patchValue(intermediary),
      error: (err) => console.error('Error loading intermediary:', err)
    });
  }

  onSubmit(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const formValue = this.form.value;

    if (this.isEditMode && this.intermediaryId) {
      this.intermediaryService.update(this.intermediaryId, formValue).subscribe({
        next: () => this.router.navigate(['/intermediaries']),
        error: (err) => console.error('Error updating intermediary:', err)
      });
    } else {
      this.intermediaryService.create(formValue).subscribe({
        next: () => this.router.navigate(['/intermediaries']),
        error: (err) => console.error('Error creating intermediary:', err)
      });
    }
  }
}
