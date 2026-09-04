import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {environment} from '../../../environments/environment';
import {Observable} from 'rxjs';
import {Caregiver} from '../caregivers/models/caregiver.model';
import {Page} from '../../shared/models/page.model';

@Injectable({
  providedIn: 'root'
})
export class CaregiverService {
  private readonly baseUrl = `${environment.apiUrl}/api/v1/caregivers`;

  constructor(private readonly http: HttpClient) { }

  getAll(page: number = 0, size: number = 20): Observable<Page<Caregiver>> {
    const params = new HttpParams()
      .set('page', page)
      .set('size', size);

    return this.http.get<Page<Caregiver>>(this.baseUrl, { params });
  }

  getById(id: number): Observable<Caregiver> {
    return this.http.get<Caregiver>(`${this.baseUrl}/${id}`);
  }

  create(caregiver: Partial<Caregiver>): Observable<Caregiver> {
    return this.http.post<Caregiver>(this.baseUrl, caregiver);
  }

  update(id: number, caregiver: Partial<Caregiver>): Observable<Caregiver> {
    return this.http.put<Caregiver>(`${this.baseUrl}/${id}`, caregiver);
  }

  deactivate(id: number): Observable<void> {
    return this.http.patch<void>(`${this.baseUrl}/${id}/deactivate`, {});
  }

  uploadPhoto(id: number, file: File): Observable<void> {
    const formData = new FormData();
    formData.append('file', file);
    return this.http.post<void>(`${this.baseUrl}/${id}/photo`, formData);
  }

  getPhoto(id: number): Observable<Blob> {
    return this.http.get(`${this.baseUrl}/${id}/photo`, { responseType: 'blob' });
  }

  deletePhoto(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}/photo`);
  }
}
