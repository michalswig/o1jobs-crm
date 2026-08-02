import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../../environments/environment';
import { Page } from '../../../shared/models/page.model';
import {CareRecipient} from '../models/care-recipient';

@Injectable({
  providedIn: 'root'
})
export class CareRecipientService {
  private readonly baseUrl = `${environment.apiUrl}/api/v1/care-recipients`;

  constructor(private readonly http: HttpClient) { }

  getAll(page: number = 0, size: number = 20): Observable<Page<CareRecipient>> {
    const params = new HttpParams()
      .set('page', page)
      .set('size', size);

    return this.http.get<Page<CareRecipient>>(this.baseUrl, { params });
  }

  getById(id: number): Observable<CareRecipient> {
    return this.http.get<CareRecipient>(`${this.baseUrl}/${id}`);
  }

  create(careRecipient: Partial<CareRecipient>): Observable<CareRecipient> {
    return this.http.post<CareRecipient>(this.baseUrl, careRecipient);
  }

  update(id: number, careRecipient: Partial<CareRecipient>): Observable<CareRecipient> {
    return this.http.put<CareRecipient>(`${this.baseUrl}/${id}`, careRecipient);
  }

  deactivate(id: number): Observable<void> {
    return this.http.patch<void>(`${this.baseUrl}/${id}/deactivate`, {});
  }
}
