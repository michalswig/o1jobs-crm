import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { DashboardData } from '../models/dashboard-data';

@Injectable({
  providedIn: 'root'
})
export class DashboardService {
  private readonly baseUrl = `${environment.apiUrl}/api/v1/dashboard`;

  constructor(private readonly http: HttpClient) { }

  getDashboard(): Observable<DashboardData> {
    return this.http.get<DashboardData>(this.baseUrl);
  }
}
