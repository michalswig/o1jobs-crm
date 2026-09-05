import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../../environments/environment';
import { Page } from '../../../shared/models/page.model';
import {Intermediary} from '../models/intermediary.interface';

@Injectable({
  providedIn: 'root'
})
export class IntermediaryService {
  private readonly baseUrl = `${environment.apiUrl}/api/v1/intermediaries`;

  constructor(private readonly http: HttpClient) { }

  getAll(page: number = 0, size: number = 20): Observable<Page<Intermediary>> {
    const params = new HttpParams()
      .set('page', page)
      .set('size', size);

    return this.http.get<Page<Intermediary>>(this.baseUrl, { params });
  }

  getById(id: number): Observable<Intermediary> {
    return this.http.get<Intermediary>(`${this.baseUrl}/${id}`);
  }

  create(intermediary: Partial<Intermediary>): Observable<Intermediary> {
    return this.http.post<Intermediary>(this.baseUrl, intermediary);
  }

  update(id: number, intermediary: Partial<Intermediary>): Observable<Intermediary> {
    return this.http.put<Intermediary>(`${this.baseUrl}/${id}`, intermediary);
  }

  deactivate(id: number): Observable<void> {
    return this.http.patch<void>(`${this.baseUrl}/${id}/deactivate`, {});
  }
}
