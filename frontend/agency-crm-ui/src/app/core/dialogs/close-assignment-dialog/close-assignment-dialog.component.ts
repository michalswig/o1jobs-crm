import { Component, inject } from '@angular/core';
import { MatDialogRef, MatDialogModule } from '@angular/material/dialog';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import { MatInputModule } from '@angular/material/input';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CloseAssignmentPayload } from '../../../crm/assignments/services/assignment.service';
import { EnumLabelPipe } from '../../../shared/pipes/enum-label.pipe';

@Component({
  selector: 'app-close-assignment-dialog',
  standalone: true,
  imports: [
    MatDialogModule,
    MatButtonModule,
    MatFormFieldModule,
    MatSelectModule,
    MatInputModule,
    ReactiveFormsModule,
    EnumLabelPipe
  ],
  templateUrl: './close-assignment-dialog.component.html',
  styleUrl: './close-assignment-dialog.component.scss'
})
export class CloseAssignmentDialogComponent {

  reasons = ['FAMILY_CANCELLED', 'SENIOR_PASSED', 'NURSING_HOME', 'OTHER'];

  form: FormGroup;

  constructor(
    private readonly fb: FormBuilder,
    private readonly dialogRef: MatDialogRef<CloseAssignmentDialogComponent>
  ) {
    this.form = this.fb.group({
      reason: ['', Validators.required],
      notes: ['']
    });
  }

  onConfirm(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }
    const payload: CloseAssignmentPayload = this.form.value;
    this.dialogRef.close(payload);
  }

  onCancel(): void {
    this.dialogRef.close(null);
  }
}
