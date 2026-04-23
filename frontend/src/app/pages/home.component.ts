import { Component, inject } from '@angular/core';
import { Router } from '@angular/router';
import { SearchBarComponent } from '../shared/search-bar.component';
import { FeatureButtonsComponent } from '../shared/feature-buttons.component';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [SearchBarComponent, FeatureButtonsComponent],
  template: `
    <div class="home">
      <app-search-bar (search)="onSearch($event)"></app-search-bar>
      <app-feature-buttons></app-feature-buttons>
    </div>
  `,
  styles: `.home { padding: 24px 0; }`
})
export class HomeComponent {
  private readonly router = inject(Router);

  onSearch(q: string): void {
    if (!q) return;
    this.router.navigate(['/search'], { queryParams: { q } });
  }
}
