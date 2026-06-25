import { Component, inject, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { LltpService } from '../core/lltp.service';
import { API_BASE } from '../core/api';
import { QrTestResult } from '../core/models';

/**
 * Dev/test tool — nhập mã hồ sơ (RECEIVE_NO), hệ thống kiểm tra Phiếu tồn tại & còn hiệu lực
 * rồi sinh mã QR tương ứng. Không cần đăng nhập.
 */
@Component({
  selector: 'app-qr-test',
  standalone: true,
  imports: [FormsModule],
  template: `
    <div class="qr-test">
      <h2>Tạo mã QR (Test)</h2>
      <p class="hint">
        Nhập mã hồ sơ (mã số Phiếu). Hệ thống kiểm tra Phiếu tồn tại và còn hiệu lực,
        sau đó tự sinh mã QR tương ứng.
      </p>

      <form (ngSubmit)="generate()" class="form">
        <input
          type="text"
          name="receiveNo"
          [(ngModel)]="receiveNo"
          placeholder="VD: PLT-2024-0001"
          autocomplete="off"
          [disabled]="loading()" />
        <button type="submit" [disabled]="loading() || !receiveNo.trim()">
          {{ loading() ? 'Đang tạo…' : 'Tạo mã QR' }}
        </button>
      </form>

      @if (error()) {
        <div class="alert error">{{ error() }}</div>
      }

      @if (result(); as r) {
        @if (r.found) {
          <div class="card">
            <div class="alert ok">Đã tìm thấy Phiếu hợp lệ cho mã <b>{{ r.receiveNo }}</b>.</div>
            <img class="qr" [src]="imgUrl(r)" alt="QR mã {{ r.receiveNo }}" />
            <label>Liên kết được mã hoá trong QR</label>
            <code class="link">{{ r.deepLink }}</code>
            <a class="download" [href]="imgUrl(r)" [download]="'qr-' + r.receiveNo + '.png'">Tải ảnh QR (.png)</a>
          </div>
        } @else {
          <div class="alert warn">
            Không tìm thấy Phiếu còn hiệu lực với mã <b>{{ r.receiveNo }}</b>.
          </div>
        }
      }
    </div>
  `,
  styles: `
    .qr-test { max-width: 640px; margin: 0 auto; padding: 40px 20px; }
    h2 { color: var(--color-maroon); font-size: 24px; margin-bottom: 8px; }
    .hint { color: var(--color-muted); font-size: 14px; margin-bottom: 24px; }
    .form { display: flex; gap: 10px; margin-bottom: 20px; }
    .form input {
      flex: 1; padding: 10px 12px; font-size: 15px;
      border: 1px solid var(--color-border); border-radius: 4px;
    }
    .form button {
      padding: 10px 20px; background: var(--color-maroon); color: #fff;
      border: none; border-radius: 4px; font-size: 15px; cursor: pointer;
    }
    .form button:disabled { opacity: 0.6; cursor: default; }
    .alert { padding: 12px 14px; border-radius: 4px; font-size: 14px; margin-bottom: 16px; }
    .alert.ok { background: #e8f5e9; color: #2e7d32; }
    .alert.warn { background: #fff3e0; color: #e65100; }
    .alert.error { background: #ffebee; color: #c62828; }
    .card {
      border: 1px solid var(--color-border); border-radius: 6px;
      padding: 24px; text-align: center;
    }
    .qr { width: 256px; height: 256px; image-rendering: pixelated; }
    label { display: block; margin: 16px 0 6px; font-size: 13px; color: var(--color-muted); }
    .link {
      display: block; word-break: break-all; font-size: 13px;
      background: #f5f5f5; padding: 8px 10px; border-radius: 4px;
    }
    .download { display: inline-block; margin-top: 16px; color: var(--color-maroon); font-size: 14px; }
  `
})
export class QrTestComponent {
  private readonly lltp = inject(LltpService);

  receiveNo = '';
  readonly loading = signal(false);
  readonly error = signal<string | null>(null);
  readonly result = signal<QrTestResult | null>(null);

  generate(): void {
    const code = this.receiveNo.trim();
    if (!code) return;
    this.loading.set(true);
    this.error.set(null);
    this.result.set(null);
    this.lltp.generateTestQr(code).subscribe({
      next: r => { this.result.set(r); this.loading.set(false); },
      error: () => { this.error.set('Không thể tạo mã QR. Vui lòng thử lại.'); this.loading.set(false); }
    });
  }

  imgUrl(r: QrTestResult): string {
    return `${API_BASE}${r.qrPngUrl}`;
  }
}
