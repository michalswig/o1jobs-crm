import {HttpErrorResponse, HttpInterceptorFn} from '@angular/common/http';
import {inject} from '@angular/core';
import {Router} from '@angular/router';
import {AuthService} from '../services/auth.service';
import {MatSnackBar} from '@angular/material/snack-bar';
import {catchError, throwError} from 'rxjs';

export const errorInterceptor: HttpInterceptorFn = (req, next) => {
  const router = inject(Router);
  const authService = inject(AuthService);
  const snackBar = inject(MatSnackBar);

  return next(req).pipe(
    catchError((error: HttpErrorResponse) => {

      if (error.status === 401) {
        authService.logout();
        router.navigate(['/login']);
      } else if (error.status === 403) {
        snackBar.open('Brak uprawnień do tej operacji.', 'Zamknij', { duration: 5000 });
      } else if (error.status === 500) {
        snackBar.open('Błąd serwera. Spróbuj ponownie później.', 'Zamknij', { duration: 5000 });
      }

      return throwError(() => error);
    })
  );
};
