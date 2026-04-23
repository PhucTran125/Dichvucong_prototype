import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-search-bar',
  standalone: true,
  imports: [FormsModule],
  template: `
    <div class="search-row">
      <div class="search-input-wrap">
        <input
          type="text"
          class="search-input"
          [(ngModel)]="value"
          (keydown.enter)="submit()"
          placeholder="Nhập từ khoá tìm kiếm"
          autocomplete="off" />
        <a class="advanced-link" href="#" (click)="onAdvancedClick($event)">Tìm kiếm nâng cao</a>
      </div>
      <button class="search-btn" (click)="submit()" aria-label="Tìm kiếm">
        <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24"
             fill="none" stroke="#555" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
          <circle cx="11" cy="11" r="7"></circle>
          <line x1="21" y1="21" x2="16.65" y2="16.65"></line>
        </svg>
      </button>
    </div>
  `,
  styles: `
    .search-row {
      display: flex;
      align-items: stretch;
      border: 1px solid var(--color-border);
      border-radius: 4px;
      background: var(--color-white);
      overflow: hidden;
    }
    .search-input-wrap {
      flex: 1;
      display: flex;
      align-items: center;
      padding: 0 18px;
      min-height: 48px;
      gap: 12px;
    }
    .search-input {
      flex: 1;
      border: none;
      outline: none;
      font-size: 15px;
      color: var(--color-text);
      background: transparent;
      font-family: inherit;
    }
    .search-input::placeholder { color: #b0a99a; }
    .advanced-link {
      font-size: 13px;
      color: var(--color-text);
      white-space: nowrap;
      padding-left: 14px;
      border-left: 1px solid var(--color-border);
    }
    .advanced-link:hover { color: var(--color-maroon); }
    .search-btn {
      width: 56px;
      background: var(--color-beige);
      border-left: 1px solid var(--color-border);
      display: flex; align-items: center; justify-content: center;
      transition: background 0.15s;
    }
    .search-btn:hover { background: #ebe7df; }
  `
})
export class SearchBarComponent {
  @Input() set initialValue(v: string) { this.value = v ?? ''; }
  @Output() readonly search = new EventEmitter<string>();
  value = '';

  submit(): void {
    this.search.emit(this.value.trim());
  }

  onAdvancedClick(event: Event): void {
    event.preventDefault();
    alert('Tìm kiếm nâng cao sẽ sớm ra mắt.');
  }
}
