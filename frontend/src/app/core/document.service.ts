import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { API_BASE } from './api';
import { DocumentDto, DocumentPreviewDto, PageResponse } from './models';

@Injectable({ providedIn: 'root' })
export class DocumentService {
  private readonly http = inject(HttpClient);

  search(q: string, page = 0, size = 20): Observable<PageResponse<DocumentPreviewDto>> {
    const params = new HttpParams()
      .set('q', q)
      .set('page', page)
      .set('size', size);
    return this.http.get<PageResponse<DocumentPreviewDto>>(`${API_BASE}/api/documents/search`, { params });
  }

  getById(id: string): Observable<DocumentDto> {
    return this.http.get<DocumentDto>(`${API_BASE}/api/documents/${id}`);
  }

  qrUrl(id: string): string {
    return `${API_BASE}/api/documents/${id}/qr.png`;
  }
}
