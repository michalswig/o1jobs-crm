import {Component} from '@angular/core';
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {AuthService} from '../../core/services/auth.service';

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


  constructor(private authService: AuthService) {
  }

  onSubmit() {
    this.authService.login(this.loginForm.value as { username: string; password: string })
      .subscribe({
        next: (response) => console.log('Sukces, token:', response.token),
        error: (err) => console.error('Błąd logowania:', err)
      });
  }
}
