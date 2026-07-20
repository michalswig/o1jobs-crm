import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Client, Page} from '../models/client.model';
import {environment} from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ClientService {
  constructor(private readonly http: HttpClient) { }

  getAll(page: number = 0, size: number = 20): Observable<Page<Client>> {
    const params = new HttpParams()
      .set('page', page)
      .set('size', size);

    return this.http.get<Page<Client>>(`${environment.apiUrl}/api/v1/clients`, { params });
  }

}
