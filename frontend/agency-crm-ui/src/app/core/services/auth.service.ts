import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../../environments/environment.development';

export interface LoginRequest {
  username: string;
  password: string;
}

export interface LoginResponse {
  token: string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private _token: string | null = null;

  constructor(private http: HttpClient) {
  }

  login(credentials: LoginRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${environment.apiUrl}/auth/login`, credentials);
  }

  get token(): string | null {
    return this._token;
  }

  set token(value: string | null) {
    this._token = value;
  }

  isLoggedIn(): boolean {
    return this._token !== null;
  }



}
