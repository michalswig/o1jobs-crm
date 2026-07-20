import {Component} from '@angular/core';
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {AuthService} from '../../core/services/auth.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-login',
  imports: [ReactiveFormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {

  loginForm = new FormGroup({
    username: new FormControl('', [Validators.required]),
    password: new FormControl('', [Validators.required])
  });


  constructor(private authService: AuthService,
              private router: Router) {
  }

  onSubmit(): void {
    this.authService.login(this.loginForm.value as { username: string; password: string })
      .subscribe({
        next: (response) => {
          this.authService.token = response.token;
          // TODO: docelowo przekierować na /dashboard, gdy powstanie jako
          // activity feed / BI hub (ostatnie działania userów CRM).
          // Tymczasowo: prosto na /clients, dopóki dashboard to pusty placeholder.
          this.router.navigate(['/clients']);
          console.log('Zalogowano, token zapisany:', this.authService.token);
        },
        error: (err) => console.error('Błąd logowania:', err)
      });
  }

}
