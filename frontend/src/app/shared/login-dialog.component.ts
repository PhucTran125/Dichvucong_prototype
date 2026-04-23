import { Component, computed, effect, inject, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../core/auth.service';
import { LoginDialogService } from '../core/login-dialog.service';

interface HttpErrorLike { error?: { message?: string }; message?: string; }

@Component({
  selector: 'app-login-dialog',
  standalone: true,
  imports: [FormsModule],
  template: `
    @if (dialog.isOpen()) {
      <div class="backdrop" (click)="cancel()">
        <div class="dialog" (click)="$event.stopPropagation()">
          <h3>Đăng nhập</h3>

          <div class="field">
            <label>Số CCCD</label>
            @if (locked()) {
              <div class="value-readonly">{{ citizenId }}</div>
            } @else {
              <input type="text" [(ngModel)]="citizenId" placeholder="Nhập số CCCD" autocomplete="off" />
            }
          </div>

          <div class="field">
            <label>Mật khẩu</label>
            <input type="password" [(ngModel)]="password" placeholder="Mật khẩu"
                   (keydown.enter)="submit()" autocomplete="current-password" />
          </div>

          @if (error(); as err) {
            <div class="error">{{ err }}</div>
          }

          <div class="actions">
            <button class="btn-cancel" (click)="cancel()" [disabled]="busy()">Hủy</button>
            <button class="btn-submit" (click)="submit()" [disabled]="busy()">
              {{ busy() ? 'Đang xử lý…' : 'Đăng nhập' }}
            </button>
          </div>
        </div>
      </div>
    }
  `,
  styles: `
    .backdrop { position: fixed; inset: 0; background: rgba(0,0,0,0.45); display: flex; align-items: center; justify-content: center; z-index: 100; padding: 20px; }
    .dialog { background: white; width: 100%; max-width: 420px; border-radius: 8px; padding: 24px 26px; box-shadow: 0 12px 32px rgba(0,0,0,0.22); }
    h3 { color: var(--color-maroon); margin: 0 0 18px 0; font-size: 20px; font-weight: 700; }
    .field { margin-bottom: 14px; }
    .field label { display: block; font-size: 11px; color: var(--color-muted); text-transform: uppercase; letter-spacing: 0.4px; margin-bottom: 6px; }
    .field input { width: 100%; padding: 10px 12px; border: 1px solid var(--color-border); border-radius: 4px; font-size: 14px; font-family: inherit; background: white; }
    .field input:focus { outline: none; border-color: var(--color-ochre); box-shadow: 0 0 0 2px rgba(217,164,65,0.18); }
    .value-readonly { padding: 10px 12px; border: 1px solid var(--color-border); border-radius: 4px; background: var(--color-beige); font-size: 14px; letter-spacing: 0.5px; color: var(--color-text); }
    .error { color: #c62828; font-size: 13px; margin: 0 0 12px 0; padding: 8px 10px; background: #fdecea; border-radius: 4px; }
    .actions { display: flex; justify-content: flex-end; gap: 10px; margin-top: 6px; }
    .btn-cancel, .btn-submit { padding: 9px 18px; border-radius: 4px; font-size: 14px; font-weight: 500; }
    .btn-cancel { border: 1px solid var(--color-border); background: white; color: var(--color-text); }
    .btn-cancel:hover:not(:disabled) { background: var(--color-beige); }
    .btn-submit { background: var(--color-ochre); color: white; }
    .btn-submit:hover:not(:disabled) { background: var(--color-ochre-hover); }
    .btn-submit:disabled, .btn-cancel:disabled { opacity: 0.55; cursor: not-allowed; }
  `
})
export class LoginDialogComponent {
  readonly dialog = inject(LoginDialogService);
  private readonly auth = inject(AuthService);
  private readonly router = inject(Router);

  citizenId = '';
  password = '';
  readonly busy = signal<boolean>(false);
  readonly error = signal<string | null>(null);
  readonly locked = computed<boolean>(() => this.dialog.prefilledCitizenId() !== null);

  constructor() {
    effect(() => {
      if (this.dialog.isOpen()) {
        this.citizenId = this.dialog.prefilledCitizenId() ?? '';
        this.password = '';
        this.error.set(null);
        this.busy.set(false);
      }
    });
  }

  async submit(): Promise<void> {
    const c = this.citizenId.trim();
    const p = this.password;
    if (!c || !p) {
      this.error.set('Vui lòng nhập đầy đủ CCCD và mật khẩu.');
      return;
    }
    this.busy.set(true);
    this.error.set(null);
    try {
      await this.auth.passwordLogin(c, p);
      const targetUrl = this.router.url;
      this.dialog.close();
      // Force-reactivate the current route so components re-run their data fetch
      // now that the JWT is in place.
      await this.router.navigateByUrl('/', { skipLocationChange: true });
      await this.router.navigateByUrl(targetUrl);
    } catch (e) {
      const err = e as HttpErrorLike;
      const msg = err?.error?.message ?? err?.message ?? 'Đăng nhập không thành công.';
      this.error.set(msg);
      this.busy.set(false);
    }
  }

  cancel(): void {
    if (this.busy()) return;
    this.dialog.close();
  }
}
