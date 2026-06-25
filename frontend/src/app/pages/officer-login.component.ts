import { Component, OnInit, inject, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '../core/auth.service';

interface HttpErrorLike { error?: { message?: string }; message?: string; }

@Component({
  selector: 'app-officer-login',
  standalone: true,
  imports: [FormsModule],
  template: `
    <div class="page">
      <div class="card">
        <h1>Đăng nhập cán bộ</h1>
        <p class="lead">Đăng nhập để xác thực Phiếu lý lịch tư pháp (Điều 17).</p>

        <div class="field">
          <label>Tên đăng nhập</label>
          <input type="text" [(ngModel)]="username" placeholder="VD: canbo" autocomplete="username" />
        </div>
        <div class="field">
          <label>Mật khẩu</label>
          <input type="password" [(ngModel)]="password" placeholder="Mật khẩu"
                 (keydown.enter)="submit()" autocomplete="current-password" />
        </div>

        @if (error(); as err) { <div class="error">{{ err }}</div> }

        <button class="btn-primary" (click)="submit()" [disabled]="busy()">
          {{ busy() ? 'Đang xử lý…' : 'Đăng nhập' }}
        </button>
        <p class="demo-hint">Tài khoản demo: <code>canbo</code> / <code>123456</code></p>
      </div>
    </div>
  `,
  styles: `
    .page { max-width: 420px; margin: 0 auto; padding: 48px 0; }
    .card { background: white; border: 1px solid var(--color-border); border-radius: 8px; padding: 28px 30px; }
    h1 { color: var(--color-maroon); font-size: 22px; margin: 0 0 4px; }
    .lead { color: var(--color-muted); font-size: 14px; margin: 0 0 22px; }
    .field { margin-bottom: 16px; }
    .field label { display: block; font-size: 11px; color: var(--color-muted); text-transform: uppercase; letter-spacing: 0.4px; margin-bottom: 6px; }
    .field input { width: 100%; padding: 10px 12px; border: 1px solid var(--color-border); border-radius: 4px; font-size: 14px; font-family: inherit; background: white; }
    .field input:focus { outline: none; border-color: var(--color-ochre); box-shadow: 0 0 0 2px rgba(217,164,65,0.18); }
    .btn-primary { width: 100%; background: var(--color-ochre); color: white; padding: 11px 22px; border-radius: 4px; font-weight: 600; font-size: 14px; margin-top: 4px; }
    .btn-primary:hover:not(:disabled) { background: var(--color-ochre-hover); }
    .btn-primary:disabled { opacity: 0.55; cursor: not-allowed; }
    .error { background: #fdecea; border: 1px solid #f5c6c2; color: #c62828; border-radius: 6px; padding: 12px 14px; margin-bottom: 14px; font-size: 14px; }
    .demo-hint { font-size: 12px; color: var(--color-muted); margin: 14px 0 0; text-align: center; }
    .demo-hint code { background: var(--color-beige); padding: 1px 6px; border-radius: 3px; border: 1px solid var(--color-border); }
  `
})
export class OfficerLoginComponent implements OnInit {
  private readonly auth = inject(AuthService);
  private readonly router = inject(Router);
  private readonly route = inject(ActivatedRoute);

  username = '';
  password = '';
  readonly busy = signal(false);
  readonly error = signal<string | null>(null);

  private returnUrl = '/verify';

  ngOnInit(): void {
    this.returnUrl = this.route.snapshot.queryParamMap.get('returnUrl') || '/verify';
    // Already logged in as cán bộ (session restored) — skip the login screen.
    if (this.auth.isOfficer()) {
      this.router.navigateByUrl(this.returnUrl);
    }
  }

  async submit(): Promise<void> {
    const u = this.username.trim();
    const p = this.password;
    if (!u || !p) {
      this.error.set('Vui lòng nhập tên đăng nhập và mật khẩu.');
      return;
    }
    this.busy.set(true);
    this.error.set(null);
    try {
      await this.auth.officerLogin(u, p);
      await this.router.navigateByUrl(this.returnUrl);
    } catch (e) {
      const err = e as HttpErrorLike;
      this.error.set(err?.error?.message ?? err?.message ?? 'Đăng nhập không thành công.');
      this.busy.set(false);
    }
  }
}
