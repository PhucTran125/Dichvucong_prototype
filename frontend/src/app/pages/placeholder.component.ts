import { Component, inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-placeholder',
  standalone: true,
  template: `
    <div class="placeholder">
      <h2>{{ title }}</h2>
      <p>Tính năng này sẽ sớm ra mắt.</p>
    </div>
  `,
  styles: `
    .placeholder { text-align: center; padding: 80px 20px; }
    h2 { color: var(--color-maroon); font-size: 24px; margin-bottom: 12px; }
    p { color: var(--color-muted); }
  `
})
export class PlaceholderComponent {
  private readonly route = inject(ActivatedRoute);
  readonly title: string = this.route.snapshot.data['title'] ?? 'Đang xây dựng';
}
