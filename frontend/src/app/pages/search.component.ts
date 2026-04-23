import { Component, OnInit, inject, signal } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { CommonModule, DatePipe } from '@angular/common';
import { SearchBarComponent } from '../shared/search-bar.component';
import { FeatureButtonsComponent } from '../shared/feature-buttons.component';
import { DocumentDetailComponent } from './document-detail.component';
import { DocumentService } from '../core/document.service';
import { DocumentDto, DocumentPreviewDto, PageResponse } from '../core/models';

@Component({
  selector: 'app-search',
  standalone: true,
  imports: [CommonModule, RouterLink, SearchBarComponent, FeatureButtonsComponent, DocumentDetailComponent, DatePipe],
  template: `
    <div class="search-page">
      <app-search-bar [initialValue]="initialQuery()" (search)="onSearch($event)"></app-search-bar>

      @if (loading()) {
        <div class="loading">Đang tải…</div>
      } @else if (idMode()) {
        @if (doc()) {
          <app-document-detail [doc]="doc()"></app-document-detail>
        } @else if (notFound()) {
          <div class="empty">Không tìm thấy hồ sơ với mã "{{ idMode() }}".</div>
        }
      } @else {
        @if (results(); as r) {
          @if (r.totalElements === 0) {
            <div class="empty">Không có hồ sơ nào khớp với từ khoá "{{ lastQuery() }}".</div>
          } @else {
            <div class="results-header">
              Tìm thấy <strong>{{ r.totalElements }}</strong> hồ sơ cho "{{ lastQuery() }}"
            </div>
            <ul class="result-list">
              @for (item of r.items; track item.id) {
                <li class="result-card">
                  <a [routerLink]="['/documents', item.id]" class="result-link">
                    <div class="result-top">
                      <span class="type-tag">{{ item.typeLabel }}</span>
                      <span class="status-tag" [class]="'status-' + item.status">{{ statusLabel(item.status) }}</span>
                    </div>
                    <div class="result-title">{{ item.title }}</div>
                    <div class="result-meta">
                      <span>Số: <strong>{{ item.documentNumber }}</strong></span>
                      <span>Chủ hồ sơ: <strong>{{ item.subjectFullNameMasked }}</strong></span>
                    </div>
                    <div class="result-auth">{{ item.issuingAuthority }} • Ngày cấp {{ item.issuedAt | date:'dd/MM/yyyy' }}</div>
                  </a>
                </li>
              }
            </ul>
          }
        } @else {
          <app-feature-buttons></app-feature-buttons>
        }
      }
    </div>
  `,
  styles: `
    .search-page { padding: 24px 0; }
    .loading, .empty { padding: 40px 0; text-align: center; color: var(--color-muted); font-size: 15px; }
    .results-header { margin-top: 20px; color: var(--color-muted); font-size: 14px; }
    .result-list { list-style: none; margin: 16px 0 0; padding: 0; display: flex; flex-direction: column; gap: 12px; }
    .result-card { border: 1px solid var(--color-border); border-radius: 6px; background: var(--color-white); transition: border-color 0.15s, box-shadow 0.15s; }
    .result-card:hover { border-color: var(--color-ochre); box-shadow: 0 2px 8px rgba(0,0,0,0.04); }
    .result-link { display: block; padding: 16px 18px; }
    .result-top { display: flex; align-items: center; gap: 10px; margin-bottom: 8px; }
    .type-tag { background: var(--color-beige); color: var(--color-maroon); font-size: 12px; padding: 3px 10px; border-radius: 12px; font-weight: 500; }
    .status-tag { font-size: 11px; padding: 3px 10px; border-radius: 12px; font-weight: 600; letter-spacing: 0.3px; }
    .status-VALID { background: #e8f5e9; color: #2e7d32; }
    .status-EXPIRED { background: #fff4e5; color: #b67800; }
    .status-REVOKED { background: #fdecea; color: #c62828; }
    .result-title { font-size: 16px; color: var(--color-maroon); font-weight: 600; margin-bottom: 6px; }
    .result-meta { display: flex; gap: 24px; font-size: 13px; color: var(--color-text); margin-bottom: 4px; flex-wrap: wrap; }
    .result-auth { font-size: 12px; color: var(--color-muted); }
  `
})
export class SearchComponent implements OnInit {
  private readonly route = inject(ActivatedRoute);
  private readonly router = inject(Router);
  private readonly docs = inject(DocumentService);

  readonly initialQuery = signal<string>('');
  readonly lastQuery = signal<string>('');
  readonly loading = signal<boolean>(false);
  readonly results = signal<PageResponse<DocumentPreviewDto> | null>(null);
  readonly doc = signal<DocumentDto | null>(null);
  readonly idMode = signal<string | null>(null);
  readonly notFound = signal<boolean>(false);

  ngOnInit(): void {
    this.route.queryParamMap.subscribe(params => {
      const id = params.get('id');
      const q = params.get('q') ?? '';
      this.initialQuery.set(q);
      if (id) {
        this.loadById(id);
      } else if (q) {
        this.runSearch(q);
      } else {
        this.results.set(null);
        this.doc.set(null);
        this.idMode.set(null);
      }
    });
  }

  onSearch(q: string): void {
    if (!q) return;
    this.router.navigate([], {
      queryParams: { q, id: null },
      queryParamsHandling: 'merge',
      replaceUrl: true
    });
  }

  private loadById(id: string): void {
    this.idMode.set(id);
    this.doc.set(null);
    this.notFound.set(false);
    this.results.set(null);
    this.loading.set(true);
    this.docs.getById(id).subscribe({
      next: d => { this.doc.set(d); this.loading.set(false); },
      error: _ => { this.notFound.set(true); this.loading.set(false); }
    });
  }

  private runSearch(q: string): void {
    this.idMode.set(null);
    this.doc.set(null);
    this.lastQuery.set(q);
    this.loading.set(true);
    this.docs.search(q).subscribe({
      next: p => { this.results.set(p); this.loading.set(false); },
      error: _ => { this.loading.set(false); }
    });
  }

  statusLabel(s: string): string {
    switch (s) {
      case 'VALID': return 'Còn hiệu lực';
      case 'EXPIRED': return 'Hết hạn';
      case 'REVOKED': return 'Đã thu hồi';
      default: return s;
    }
  }
}
