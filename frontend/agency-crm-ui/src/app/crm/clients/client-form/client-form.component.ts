import { Component, OnInit, inject } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';
import { MatCardModule } from '@angular/material/card';
import { ClientService } from '../../services/client.service';
import { Client } from '../../models/client.model';
import { IntermediaryService } from '../../intermediaries/services/intermediary.service';
import {Intermediary} from '../../intermediaries/models/intermediary.interface';

@Component({
  selector: 'app-client-form',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    RouterLink,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatSelectModule,
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
  private readonly intermediaryService = inject(IntermediaryService);

  clientId: number | null = null;
  isEditMode = false;
  intermediaries: Intermediary[] = [];

  form: FormGroup = this.fb.group({
    name: ['', Validators.required],
    email: ['', [Validators.required, Validators.email]],
    phoneNumber: ['', Validators.required],
    country: ['', Validators.required],
    city: ['', Validators.required],
    postalCode: ['', Validators.required],
    streetAddress: ['', Validators.required],
    notes: [''],
    intermediary_id: ['']
  });

  ngOnInit(): void {
    this.loadIntermediaries();

    const idParam = this.route.snapshot.paramMap.get('id');

    if (idParam) {
      this.isEditMode = true;
      this.clientId = Number(idParam);
      this.loadClient(this.clientId);
    }
  }

  loadIntermediaries(): void {
    // Pobieramy jednorazowo do 100 partnerów dla dropdowna - wystarczające na tym etapie,
    // do rozważenia autouzupełnianie z wyszukiwaniem, gdy lista partnerów mocno urośnie.
    this.intermediaryService.getAll(0, 100).subscribe({
      next: (page) => this.intermediaries = page.content,
      error: (err) => console.error('Error loading intermediaries:', err)
    });
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
