import {Component} from '@angular/core';
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {AuthService} from '../../core/services/auth.service';
import {Router} from '@angular/router';
import {HttpErrorResponse} from '@angular/common/http';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {MatButtonModule} from '@angular/material/button';
import {MatCardModule} from '@angular/material/card';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatCardModule,
    MatProgressSpinnerModule
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {

  loginForm = new FormGroup({
    username: new FormControl('', [Validators.required]),
    password: new FormControl('', [Validators.required])
  });

  loading = false;
  errorMessage: string | null = null;

  constructor(private readonly authService: AuthService,
              private readonly router: Router) {
  }

  onSubmit(): void {
    if (this.loginForm.invalid || this.loading) {
      return;
    }

    this.loading = true;
    this.errorMessage = null;

    this.authService.login(this.loginForm.value as { username: string; password: string })
      .subscribe({
        next: (response) => {
          this.authService.token = response.token;
          this.loading = false;
          // TODO: docelowo przekierować na /dashboard, gdy powstanie jako
          // activity feed / BI hub (ostatnie działania userów CRM).
          // Tymczasowo: prosto na /clients, dopóki dashboard to pusty placeholder.
          this.router.navigate(['/clients']);
        },
        error: (err: HttpErrorResponse) => {
          this.loading = false;
          this.errorMessage = err.status === 401
            ? 'Nieprawidłowa nazwa użytkownika lub hasło.'
            : 'Nie udało się zalogować. Spróbuj ponownie.';
        }
      });
  }

}
