import { Component } from '@angular/core';

@Component({
  selector: 'app-feature-buttons',
  standalone: true,
  template: `
    <div class="feature-row">
      <button class="feature-btn">Dịch vụ công trực tuyến</button>
      <button class="feature-btn">Dịch vụ công trực tuyến của Đảng</button>
      <button class="feature-btn">Dịch vụ công liên thông: Khai sinh, Khai tử</button>
    </div>
  `,
  styles: `
    .feature-row {
      display: grid;
      grid-template-columns: repeat(3, 1fr);
      gap: 20px;
      margin-top: 24px;
    }
    .feature-btn {
      background: var(--color-ochre);
      color: var(--color-white);
      padding: 22px 16px;
      border-radius: 4px;
      font-size: 15px;
      font-weight: 500;
      line-height: 1.3;
      min-height: 82px;
      transition: background 0.15s;
    }
    .feature-btn:hover { background: var(--color-ochre-hover); }
  `
})
export class FeatureButtonsComponent {}
