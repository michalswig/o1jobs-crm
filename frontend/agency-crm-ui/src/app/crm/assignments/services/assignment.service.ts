import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../../environments/environment';
import { Page } from '../../../shared/models/page.model';
import { AssignmentCloseReason } from '../../../shared/models/domain-enums.model';
import {Assignment} from '../models/assignment';
import {AssignmentDetail} from '../models/assignment-detail';

export interface CloseAssignmentPayload {
  reason: AssignmentCloseReason;
  notes?: string;
}

@Injectable({
  providedIn: 'root'
})
export class AssignmentService {
  private readonly baseUrl = `${environment.apiUrl}/api/v1/assignments`;

  constructor(private readonly http: HttpClient) { }

  getAll(page: number = 0, size: number = 20): Observable<Page<Assignment>> {
    const params = new HttpParams()
      .set('page', page)
      .set('size', size);

    return this.http.get<Page<Assignment>>(this.baseUrl, { params });
  }

  getById(id: number): Observable<Assignment> {
    return this.http.get<Assignment>(`${this.baseUrl}/${id}`);
  }

  create(assignment: Partial<Assignment>): Observable<Assignment> {
    return this.http.post<Assignment>(this.baseUrl, assignment);
  }

  update(id: number, assignment: Partial<Assignment>): Observable<Assignment> {
    return this.http.put<Assignment>(`${this.baseUrl}/${id}`, assignment);
  }

  close(id: number, payload: CloseAssignmentPayload): Observable<void> {
    return this.http.patch<void>(`${this.baseUrl}/${id}/close`, payload);
  }

  getDetailById(id: number): Observable<AssignmentDetail> {
    return this.http.get<AssignmentDetail>(`${this.baseUrl}/${id}/details`);
  }
}
