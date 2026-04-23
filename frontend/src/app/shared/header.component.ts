import { Component, inject } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../core/auth.service';
import { LoginDialogService } from '../core/login-dialog.service';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [RouterLink],
  template: `
    <header class="site-header">
      <div class="container">
        <a routerLink="/" class="brand">
          <div class="emblem" aria-hidden="true">
            <div class="emblem-star"></div>
          </div>
          <div class="brand-text">
            <h1>CỔNG DỊCH VỤ CÔNG QUỐC GIA</h1>
            <p>Kết nối, cung cấp thông tin và dịch vụ công mọi lúc, mọi nơi</p>
          </div>
        </a>
        @if (auth.isAuthenticated()) {
          <div class="user-menu">
            <span class="user-name">{{ auth.user()?.fullName }}</span>
            <button class="btn-login" (click)="logout()">Đăng xuất</button>
          </div>
        } @else {
          <button class="btn-login" (click)="login()">Đăng nhập</button>
        }
      </div>
    </header>
  `,
  styles: `
    .site-header {
      background: var(--color-white);
      border-bottom: 1px solid var(--color-border);
    }
    .container {
      max-width: 1140px;
      margin: 0 auto;
      padding: 18px 16px;
      display: flex;
      align-items: center;
      justify-content: space-between;
      gap: 20px;
    }
    .brand {
      display: flex;
      align-items: center;
      gap: 18px;
      color: inherit;
    }
    .emblem {
      width: 76px;
      height: 76px;
      border-radius: 50%;
      background: radial-gradient(circle at center, var(--color-ochre) 0 12%, var(--color-maroon) 14% 100%);
      flex-shrink: 0;
      border: 2px solid var(--color-maroon-dark);
      display: flex;
      align-items: center;
      justify-content: center;
      position: relative;
    }
    .emblem-star {
      width: 20px;
      height: 20px;
      background: var(--color-ochre);
      clip-path: polygon(50% 0%, 61% 35%, 98% 35%, 68% 57%, 79% 91%, 50% 70%, 21% 91%, 32% 57%, 2% 35%, 39% 35%);
    }
    .brand-text h1 {
      font-size: 22px;
      color: var(--color-maroon);
      margin: 0 0 4px 0;
      letter-spacing: 0.5px;
      font-weight: 700;
    }
    .brand-text p {
      font-size: 13px;
      color: var(--color-maroon);
      font-style: italic;
      margin: 0;
    }
    .btn-login {
      border: 1.5px solid var(--color-ochre);
      color: var(--color-maroon);
      padding: 9px 22px;
      border-radius: 4px;
      font-weight: 500;
      font-size: 14px;
      transition: all 0.15s;
      background: var(--color-white);
    }
    .btn-login:hover {
      background: var(--color-ochre);
      color: var(--color-white);
    }
    .user-menu { display: flex; align-items: center; gap: 12px; }
    .user-name { font-size: 13px; color: var(--color-maroon); font-weight: 500; }
  `
})
export class HeaderComponent {
  readonly auth = inject(AuthService);
  private readonly router = inject(Router);
  private readonly loginDialog = inject(LoginDialogService);

  login(): void { this.loginDialog.open(); }
  logout(): void { this.auth.logout(); this.router.navigate(['/']); }
}
