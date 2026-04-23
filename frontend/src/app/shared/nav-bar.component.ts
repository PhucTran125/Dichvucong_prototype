import { Component } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';

@Component({
  selector: 'app-nav-bar',
  standalone: true,
  imports: [RouterLink, RouterLinkActive],
  template: `
    <nav class="nav-bar">
      <div class="container">
        <a routerLink="/" class="home-icon" title="Trang chủ">
          <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24" fill="white">
            <path d="M12 3l9 8h-3v9h-4v-6h-4v6H6v-9H3z"/>
          </svg>
        </a>
        <a routerLink="/" class="nav-item" routerLinkActive="active" [routerLinkActiveOptions]="{exact: true}">
          Thông tin và dịch vụ <span class="caret">▾</span>
        </a>
        <a routerLink="/payments" class="nav-item" routerLinkActive="active">Thanh toán trực tuyến</a>
        <a routerLink="/feedback" class="nav-item" routerLinkActive="active">
          Phản ánh kiến nghị <span class="caret">▾</span>
        </a>
        <a routerLink="/help" class="nav-item" routerLinkActive="active">
          Hỗ trợ <span class="caret">▾</span>
        </a>
      </div>
    </nav>
  `,
  styles: `
    .nav-bar { background: var(--color-beige); border-bottom: 1px solid var(--color-border); }
    .container { max-width: 1140px; margin: 0 auto; display: flex; align-items: stretch; }
    .home-icon {
      background: var(--color-ochre);
      width: 50px; height: 50px;
      display: flex; align-items: center; justify-content: center;
    }
    .nav-item {
      padding: 0 20px;
      display: flex; align-items: center;
      color: var(--color-text);
      font-size: 14px;
      transition: color 0.15s;
    }
    .nav-item:hover { color: var(--color-maroon); }
    .nav-item.active { color: var(--color-maroon); font-weight: 500; }
    .caret { font-size: 10px; margin-left: 6px; opacity: 0.7; }
  `
})
export class NavBarComponent {}
