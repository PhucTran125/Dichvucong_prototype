import { Component, OnInit, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '../core/auth.service';
import { LltpService } from '../core/lltp.service';
import { CertificateViewComponent } from '../shared/certificate-view.component';
import { DemoCertificateCode, VerifyResultDto } from '../core/models';

// Demo captcha alphabet — omits easily-confused chars (O/0, I/1).
const CAPTCHA_CHARS = 'ABCDEFGHJKLMNPQRSTUVWXYZ23456789';

@Component({
  selector: 'app-verification',
  standalone: true,
  imports: [CommonModule, FormsModule, CertificateViewComponent],
  template: `
    <div class="page">
      <h1>Xác thực Phiếu lý lịch tư pháp</h1>
      <p class="lead">Điều 17 — Cán bộ kiểm tra tính chính xác, hợp pháp của Phiếu lý lịch tư pháp.</p>

      @if (auth.user(); as u) {
        <div class="officer-banner">
          Cán bộ: <strong>{{ u.fullName }}</strong>
          <button class="btn-link" (click)="logout()">Đăng xuất</button>
        </div>
      }

      <div class="panel">
        <p class="hint">Nhập 04 thông tin trên Phiếu lý lịch tư pháp và mã captcha để kiểm tra.</p>
        <div class="form">
          <div class="field"><label>Mã số Phiếu lý lịch tư pháp</label>
            <input [(ngModel)]="certificateNumber" placeholder="VD: PLT-2024-0001" /></div>
          <div class="field"><label>Họ và tên</label>
            <input [(ngModel)]="fullName" placeholder="Họ và tên người được cấp Phiếu" /></div>
          <div class="field"><label>Ngày sinh</label>
            <input [(ngModel)]="dateOfBirth" placeholder="dd/mm/yyyy" /></div>
          <div class="field"><label>Giới tính</label>
            <select [(ngModel)]="gender">
              <option value="">-- Chọn --</option>
              <option value="1">Nam</option>
              <option value="0">Nữ</option>
            </select></div>
          <div class="field span2"><label>Mã captcha</label>
            <div class="captcha-row">
              <span class="captcha-text">{{ captchaText() }}</span>
              <button type="button" class="btn-refresh" (click)="refreshCaptcha()" title="Tạo mã mới">↻</button>
              <input [(ngModel)]="captchaInput" placeholder="Nhập mã bên trái"
                     (keydown.enter)="submitFields()" autocomplete="off" />
            </div>
          </div>
        </div>
        <button class="btn-primary" (click)="submitFields()" [disabled]="busy()">
          {{ busy() ? 'Đang kiểm tra…' : 'Xác thực' }}
        </button>
      </div>

      <!-- ========================== Result ========================== -->
      @if (error(); as e) {
        <div class="error">{{ e }}</div>
      }
      @if (result(); as r) {
        @if (r.valid && r.certificate) {
          <div class="result"><app-certificate-view [certificate]="r.certificate" [verifiedVia]="r.verifiedVia" /></div>
        } @else if (!r.valid) {
          <div class="error">{{ r.message || 'Phiếu lý lịch tư pháp không hợp lệ.' }}</div>
        }
      }

      <!-- ========================== Demo helper ========================== -->
      <div class="demo">
        @if (!demo()) {
          <button class="btn-link" (click)="loadDemo()">▸ Dữ liệu thử nghiệm (demo)</button>
        } @else {
          <button class="btn-link" (click)="demo.set(null)">▾ Ẩn dữ liệu thử nghiệm</button>
          <div class="demo-list">
            @for (d of demo(); track d.certificateNumber) {
              <div class="demo-item">
                <div class="demo-info">
                  <strong>{{ d.fullName }}</strong> · {{ d.certificateNumber }} · {{ d.gender }} · {{ d.dateOfBirth }}
                  <div class="demo-code">{{ d.crimeStatus }}</div>
                </div>
                <div class="demo-actions">
                  <button class="btn-link" (click)="useForFields(d)">Điền vào biểu mẫu</button>
                  <button class="btn-link" (click)="openWithQr(d)">Mở bằng QR</button>
                </div>
              </div>
            }
          </div>
        }
      </div>
    </div>
  `,
  styles: `
    .page { max-width: 880px; margin: 0 auto; padding: 24px 0 48px; }
    h1 { color: var(--color-maroon); font-size: 24px; margin: 0 0 4px; }
    .lead { color: var(--color-muted); font-size: 14px; margin: 0 0 20px; }
    .officer-banner { background: #eef7ee; border: 1px solid #c6e3c6; border-radius: 6px; padding: 10px 14px; font-size: 13px; color: var(--color-text); margin-bottom: 16px; display: flex; justify-content: space-between; align-items: center; }
    .panel { background: white; border: 1px solid var(--color-border); border-radius: 8px; padding: 22px 24px; }
    .hint { font-size: 14px; color: var(--color-text); margin: 0 0 16px; line-height: 1.6; }
    .form { display: grid; grid-template-columns: repeat(2, 1fr); gap: 14px 22px; margin-bottom: 18px; }
    .field { display: flex; flex-direction: column; }
    .field.span2 { grid-column: span 2; }
    .field label { font-size: 11px; color: var(--color-muted); text-transform: uppercase; letter-spacing: 0.4px; margin-bottom: 6px; }
    .field input, .field select { padding: 10px 12px; border: 1px solid var(--color-border); border-radius: 4px; font-size: 14px; font-family: inherit; background: white; }
    .field input:focus, .field select:focus { outline: none; border-color: var(--color-ochre); box-shadow: 0 0 0 2px rgba(217,164,65,0.18); }
    .captcha-row { display: flex; align-items: center; gap: 10px; }
    .captcha-text { font-family: 'Courier New', monospace; font-size: 22px; font-weight: 700; letter-spacing: 6px; color: var(--color-maroon); background: linear-gradient(135deg, #f3ecd9 0%, #e7dcc0 100%); border: 1px solid var(--color-border); border-radius: 4px; padding: 6px 14px; user-select: none; font-style: italic; text-decoration: line-through; text-decoration-color: rgba(0,0,0,0.18); }
    .btn-refresh { font-size: 18px; line-height: 1; padding: 6px 10px; border: 1px solid var(--color-border); border-radius: 4px; background: white; color: var(--color-maroon); cursor: pointer; }
    .btn-refresh:hover { background: var(--color-beige); }
    .captcha-row input { flex: 1; padding: 10px 12px; border: 1px solid var(--color-border); border-radius: 4px; font-size: 14px; font-family: inherit; background: white; }
    .captcha-row input:focus { outline: none; border-color: var(--color-ochre); box-shadow: 0 0 0 2px rgba(217,164,65,0.18); }
    .btn-primary { background: var(--color-ochre); color: white; padding: 11px 22px; border-radius: 4px; font-weight: 600; font-size: 14px; }
    .btn-primary:hover:not(:disabled) { background: var(--color-ochre-hover); }
    .btn-primary:disabled { opacity: 0.55; cursor: not-allowed; }
    .btn-link { background: none; color: var(--color-maroon); font-size: 13px; font-weight: 500; padding: 4px 0; text-decoration: underline; }
    .error { background: #fdecea; border: 1px solid #f5c6c2; color: #c62828; border-radius: 6px; padding: 14px 18px; margin-top: 18px; font-size: 14px; }
    .result { margin-top: 20px; }
    .demo { margin-top: 26px; border-top: 1px dashed var(--color-border); padding-top: 14px; }
    .demo-list { display: flex; flex-direction: column; gap: 8px; margin-top: 10px; }
    .demo-item { display: flex; justify-content: space-between; align-items: center; gap: 16px; border: 1px solid var(--color-border); border-radius: 6px; padding: 10px 14px; background: var(--color-beige); }
    .demo-info { font-size: 13px; }
    .demo-code { font-size: 12px; color: var(--color-muted); margin-top: 4px; }
    .demo-actions { display: flex; gap: 14px; flex-shrink: 0; }
    @media (max-width: 640px) { .form { grid-template-columns: 1fr; } .field.span2 { grid-column: span 1; } }
  `
})
export class VerificationComponent implements OnInit {
  private readonly route = inject(ActivatedRoute);
  private readonly router = inject(Router);
  private readonly lltp = inject(LltpService);
  readonly auth = inject(AuthService);

  readonly busy = signal(false);
  readonly error = signal<string | null>(null);
  readonly result = signal<VerifyResultDto | null>(null);
  readonly demo = signal<DemoCertificateCode[] | null>(null);

  // 04 holder fields printed on the certificate
  certificateNumber = '';
  fullName = '';
  dateOfBirth = '';
  gender = '';

  // demo captcha
  readonly captchaText = signal<string>(this.generateCaptcha());
  captchaInput = '';

  ngOnInit(): void {
    // The QR on the physical Phiếu carries ?doc=<receive_no> — pre-fill the certificate number.
    this.route.queryParamMap.subscribe(q => {
      const doc = q.get('doc');
      if (doc) this.certificateNumber = doc;
    });
  }

  submitFields(): void {
    if (!this.certificateNumber.trim() || !this.fullName.trim() || !this.dateOfBirth.trim() || !this.gender) {
      this.error.set('Vui lòng nhập đầy đủ 04 thông tin trên Phiếu.');
      this.result.set(null);
      return;
    }
    if (this.captchaInput.trim().toUpperCase() !== this.captchaText().toUpperCase()) {
      this.error.set('Mã captcha không đúng.');
      this.result.set(null);
      this.refreshCaptcha();
      return;
    }
    this.busy.set(true);
    this.error.set(null);
    this.result.set(null);
    this.lltp.verifyByFields({
      certificateNumber: this.certificateNumber.trim(),
      fullName: this.fullName.trim(),
      dateOfBirth: this.dateOfBirth.trim(),
      gender: this.gender
    }).subscribe({
      next: r => { this.result.set(r); this.busy.set(false); this.refreshCaptcha(); this.captchaInput = ''; },
      error: () => { this.busy.set(false); this.error.set('Có lỗi xảy ra khi tra cứu.'); this.refreshCaptcha(); }
    });
  }

  refreshCaptcha(): void {
    this.captchaText.set(this.generateCaptcha());
  }

  private generateCaptcha(): string {
    let s = '';
    for (let i = 0; i < 5; i++) {
      s += CAPTCHA_CHARS.charAt(Math.floor(Math.random() * CAPTCHA_CHARS.length));
    }
    return s;
  }

  logout(): void {
    this.auth.logout();
    this.result.set(null);
    this.router.navigate(['/dang-nhap-can-bo']);
  }

  loadDemo(): void {
    this.lltp.demoCodes().subscribe({
      next: list => this.demo.set(list),
      error: () => this.error.set('Không tải được dữ liệu thử nghiệm (đã tắt expose-codes?).')
    });
  }

  useForFields(d: DemoCertificateCode): void {
    this.certificateNumber = d.certificateNumber;
    this.fullName = d.fullName;
    this.dateOfBirth = d.dateOfBirth;
    this.gender = d.gender === 'Nữ' ? '0' : (d.gender === 'Nam' ? '1' : '');
    this.captchaInput = this.captchaText(); // convenience for the demo
    this.result.set(null);
    this.error.set(null);
  }

  openWithQr(d: DemoCertificateCode): void {
    this.router.navigate([], {
      relativeTo: this.route,
      queryParams: { doc: d.certificateNumber }
    });
  }
}
