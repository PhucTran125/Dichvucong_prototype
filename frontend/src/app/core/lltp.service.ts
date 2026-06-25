import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { API_BASE } from './api';
import { DemoCertificateCode, QrTestResult, VerifyByFieldsRequest, VerifyResultDto } from './models';

/** Client for the Phiếu lý lịch tư pháp verification API (Điều 17). */
@Injectable({ providedIn: 'root' })
export class LltpService {
  private readonly http = inject(HttpClient);

  /** Verify by the 04 printed holder fields. */
  verifyByFields(req: VerifyByFieldsRequest): Observable<VerifyResultDto> {
    return this.http.post<VerifyResultDto>(`${API_BASE}/api/lltp/verify`, req);
  }

  certificateQrUrl(documentNo: string): string {
    return `${API_BASE}/api/lltp/certificates/${encodeURIComponent(documentNo)}/qr.png`;
  }

  /** Dev-only: seeded certificates + their derived auth codes. */
  demoCodes(): Observable<DemoCertificateCode[]> {
    return this.http.get<DemoCertificateCode[]>(`${API_BASE}/api/lltp/_demo/codes`);
  }

  /** Dev/test: generate a QR for an existing, active mã số Phiếu (no auth). */
  generateTestQr(receiveNo: string): Observable<QrTestResult> {
    const params = new HttpParams().set('receiveNo', receiveNo);
    return this.http.get<QrTestResult>(`${API_BASE}/api/lltp/_demo/qr`, { params });
  }
}
