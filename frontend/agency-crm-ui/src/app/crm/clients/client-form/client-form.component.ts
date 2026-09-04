import { Component, OnInit, inject } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { ClientService } from '../../services/client.service';
import { Client } from '../../models/client.model';

@Component({
  selector: 'app-client-form',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    RouterLink,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatCardModule
  ],
  templateUrl: './client-form.component.html',
  styleUrl: './client-form.component.scss'
})
export class ClientFormComponent implements OnInit {

  private readonly fb = inject(FormBuilder);
  private readonly route = inject(ActivatedRoute);
  private readonly router = inject(Router);
  private readonly clientService = inject(ClientService);

  clientId: number | null = null;
  isEditMode = false;

  form: FormGroup = this.fb.group({
    name: ['', Validators.required],
    email: ['', [Validators.required, Validators.email]],
    phoneNumber: ['', Validators.required],
    country: ['', Validators.required],
    city: ['', Validators.required],
    postalCode: ['', Validators.required],
    streetAddress: ['', Validators.required],
    notes: ['']
  });

  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('id');

    if (idParam) {
      this.isEditMode = true;
      this.clientId = Number(idParam);
      this.loadClient(this.clientId);
    }
  }

  loadClient(id: number): void {
    this.clientService.getById(id).subscribe({
      next: (client: Client) => {
        this.form.patchValue(client);
      },
      error: (err) => console.error('Error loading client:', err)
    });
  }

  onSubmit(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const formValue = this.form.value;

    if (this.isEditMode && this.clientId) {
      this.clientService.update(this.clientId, formValue).subscribe({
        next: () => this.router.navigate(['/clients']),
        error: (err) => console.error('Error updating client:', err)
      });
    } else {
      this.clientService.create(formValue).subscribe({
        next: () => this.router.navigate(['/clients']),
        error: (err) => console.error('Error creating client:', err)
      });
    }
  }
}
