import { Component, Input, OnInit, computed, inject, signal } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { DocumentService } from '../core/document.service';
import { LoginDialogService } from '../core/login-dialog.service';
import { DocumentDto, DocumentFullDto, DocumentPreviewDto } from '../core/models';

@Component({
  selector: 'app-document-detail',
  standalone: true,
  imports: [CommonModule, DatePipe],
  template: `
    @if (docSig(); as d) {
      <div class="doc-detail">
        <div class="header">
          <div>
            <div class="type">{{ d.typeLabel }}</div>
            <h2>{{ d.title }}</h2>
            <div class="doc-number">Số hồ sơ: <strong>{{ d.documentNumber }}</strong></div>
          </div>
          <div class="header-right">
            <span class="status-tag" [class]="'status-' + d.status">{{ statusLabel(d.status) }}</span>
            <img class="qr" [src]="qrUrl()" alt="QR code" />
          </div>
        </div>

        <div class="grid">
          <div class="field"><label>Cơ quan cấp</label><div>{{ d.issuingAuthority }}</div></div>
          <div class="field"><label>Ngày cấp</label><div>{{ d.issuedAt | date:'dd/MM/yyyy' }}</div></div>
          @if (full(); as f) {
            <div class="field"><label>Họ và tên chủ hồ sơ</label><div>{{ f.subjectFullName }}</div></div>
            <div class="field"><label>Ngày sinh</label><div>{{ f.subjectDob | date:'dd/MM/yyyy' }}</div></div>
            <div class="field"><label>Số CCCD</label><div>{{ f.subjectCitizenId }}</div></div>
            <div class="field address"><label>Địa chỉ</label><div>{{ f.subjectAddress }}</div></div>
          } @else {
            @if (preview(); as p) {
              <div class="field"><label>Họ và tên chủ hồ sơ</label><div>{{ p.subjectFullNameMasked }}</div></div>
              <div class="field"><label>Số CCCD</label><div>{{ p.subjectCitizenId }}</div></div>
            }
          }
        </div>

        @if (full(); as f) {
          <div class="body-section">
            <label>Nội dung hồ sơ</label>
            <div class="body-content">{{ f.body }}</div>
          </div>
          @if (f.attachments.length > 0) {
            <div class="attachments-section">
              <label>Tài liệu đính kèm ({{ f.attachments.length }})</label>
              <ul class="attachment-list">
                @for (a of f.attachments; track a.id) {
                  <li>
                    <span class="att-name">{{ a.fileName }}</span>
                    <span class="att-size">{{ formatSize(a.sizeBytes) }}</span>
                  </li>
                }
              </ul>
            </div>
          }
        } @else {
          <div class="cta">
            <div class="cta-text">Bạn đang xem bản rút gọn. <strong>Đăng nhập bằng VNeID</strong> để xem đầy đủ thông tin.</div>
            <button class="btn-cta" (click)="login()">Đăng nhập</button>
          </div>
        }
      </div>
    } @else if (loading()) {
      <div class="loading">Đang tải hồ sơ…</div>
    } @else if (notFound()) {
      <div class="empty">Không tìm thấy hồ sơ.</div>
    }
  `,
  styles: `
    .doc-detail { background: white; border: 1px solid var(--color-border); border-radius: 8px; padding: 24px 28px; margin-top: 20px; }
    .header { display: flex; justify-content: space-between; align-items: flex-start; gap: 20px; padding-bottom: 18px; border-bottom: 1px solid var(--color-border); }
    .type { color: var(--color-muted); font-size: 12px; text-transform: uppercase; letter-spacing: 0.5px; margin-bottom: 4px; }
    h2 { color: var(--color-maroon); font-size: 22px; margin: 0 0 8px 0; }
    .doc-number { font-size: 13px; color: var(--color-muted); }
    .header-right { display: flex; flex-direction: column; align-items: flex-end; gap: 12px; }
    .qr { width: 110px; height: 110px; border: 1px solid var(--color-border); padding: 4px; background: white; border-radius: 4px; }
    .status-tag { font-size: 11px; padding: 4px 12px; border-radius: 12px; font-weight: 600; letter-spacing: 0.3px; }
    .status-VALID { background: #e8f5e9; color: #2e7d32; }
    .status-EXPIRED { background: #fff4e5; color: #b67800; }
    .status-REVOKED { background: #fdecea; color: #c62828; }
    .grid { display: grid; grid-template-columns: repeat(2, 1fr); gap: 16px 28px; padding: 20px 0; }
    .field label { display: block; font-size: 11px; color: var(--color-muted); text-transform: uppercase; letter-spacing: 0.4px; margin-bottom: 4px; }
    .field div { font-size: 14px; color: var(--color-text); }
    .field.address { grid-column: span 2; }
    .body-section { border-top: 1px solid var(--color-border); padding-top: 18px; }
    .body-section > label { display: block; font-size: 11px; color: var(--color-muted); text-transform: uppercase; letter-spacing: 0.4px; margin-bottom: 10px; }
    .body-content { font-size: 14px; line-height: 1.7; color: var(--color-text); white-space: pre-wrap; }
    .attachments-section { margin-top: 22px; border-top: 1px solid var(--color-border); padding-top: 18px; }
    .attachments-section > label { display: block; font-size: 11px; color: var(--color-muted); text-transform: uppercase; letter-spacing: 0.4px; margin-bottom: 10px; }
    .attachment-list { list-style: none; margin: 0; padding: 0; display: flex; flex-direction: column; gap: 6px; }
    .attachment-list li { display: flex; justify-content: space-between; padding: 10px 14px; border: 1px solid var(--color-border); border-radius: 4px; font-size: 13px; }
    .att-size { color: var(--color-muted); }
    .cta { background: #fffaf0; border: 1px solid #ffe1a6; border-radius: 6px; padding: 16px 20px; margin-top: 20px; display: flex; align-items: center; justify-content: space-between; gap: 20px; flex-wrap: wrap; }
    .cta-text { font-size: 14px; color: var(--color-text); }
    .btn-cta { background: var(--color-ochre); color: white; padding: 10px 20px; border-radius: 4px; font-weight: 500; font-size: 14px; }
    .btn-cta:hover { background: var(--color-ochre-hover); }
    .loading, .empty { padding: 40px 0; text-align: center; color: var(--color-muted); }
  `
})
export class DocumentDetailComponent implements OnInit {
  private readonly route = inject(ActivatedRoute);
  private readonly docs = inject(DocumentService);
  private readonly loginDialog = inject(LoginDialogService);

  readonly docSig = signal<DocumentDto | null>(null);
  readonly loading = signal<boolean>(false);
  readonly notFound = signal<boolean>(false);

  @Input() set doc(v: DocumentDto | null) { this.docSig.set(v); }

  readonly full = computed<DocumentFullDto | null>(() => {
    const d = this.docSig();
    return d && !d.preview ? d : null;
  });
  readonly preview = computed<DocumentPreviewDto | null>(() => {
    const d = this.docSig();
    return d && d.preview ? d : null;
  });
  readonly qrUrl = computed<string>(() => {
    const d = this.docSig();
    return d ? this.docs.qrUrl(d.id) : '';
  });

  ngOnInit(): void {
    this.route.paramMap.subscribe(p => {
      const id = p.get('id');
      if (id && !this.docSig()) {
        this.loading.set(true);
        this.docs.getById(id).subscribe({
          next: d => { this.docSig.set(d); this.loading.set(false); },
          error: _ => { this.notFound.set(true); this.loading.set(false); }
        });
      }
    });
  }

  login(): void {
    const p = this.preview();
    this.loginDialog.open(p ? p.subjectCitizenId : null);
  }

  statusLabel(s: string): string {
    switch (s) {
      case 'VALID': return 'Còn hiệu lực';
      case 'EXPIRED': return 'Hết hạn';
      case 'REVOKED': return 'Đã thu hồi';
      default: return s;
    }
  }

  formatSize(bytes: number): string {
    if (bytes < 1024) return bytes + ' B';
    if (bytes < 1024 * 1024) return (bytes / 1024).toFixed(1) + ' KB';
    return (bytes / 1024 / 1024).toFixed(2) + ' MB';
  }
}
