import { Component, Input, computed, inject, signal } from '@angular/core';
import { CertificateDto } from '../core/models';
import { LltpService } from '../core/lltp.service';

/**
 * Renders a verified Phiếu lý lịch tư pháp SỐ 1 to look like the official printed form
 * (Mẫu số 07/2025/LLTP) — the phôi background is an <img> layer (prints reliably as PDF),
 * with the certificate text laid over it. "In phiếu" triggers the browser print-to-PDF.
 */
@Component({
  selector: 'app-certificate-view',
  standalone: true,
  template: `
    @if (cert(); as c) {
      <div class="cert-toolbar">
        <span class="badge">✔ Phiếu hợp lệ, hợp pháp</span>
        @if (verifiedVia) { <span class="via">Hình thức xác thực: {{ viaLabel(verifiedVia) }}</span> }
        <span class="spacer"></span>
        <button class="btn-print" (click)="print()">🖨 In phiếu (PDF)</button>
      </div>

      <div class="phieu">
        <img class="phoi-bg" src="assets/phoi-phieu-1.jpg" alt="" aria-hidden="true" />
        <div class="phieu-body">
          <div class="mau-so">Mẫu số 07/2025/LLTP</div>

          <div class="head">
            <div class="head-left">
              <div class="b agency">{{ agencyUpper(c) }}</div>
              <div class="hr hr-left"></div>
            </div>
            <div class="head-right">
              <div class="b">CỘNG HÒA XÃ HỘI CHỦ NGHĨA VIỆT NAM</div>
              <div class="b">Độc lập - Tự do - Hạnh phúc</div>
              <div class="hr hr-right"></div>
            </div>
          </div>

          <div class="subhead">
            <div>Số: {{ c.certificateNumber }}</div>
            <div class="i">{{ headerDate(c) }}</div>
          </div>

          <h2 class="title">PHIẾU LÝ LỊCH TƯ PHÁP SỐ 1</h2>

          <div class="cancu i">
            <p>Căn cứ Luật Lý lịch tư pháp ngày 17 tháng 6 năm 2009;</p>
            <p>Căn cứ Nghị định số 111/2010/NĐ-CP ngày 23 tháng 11 năm 2010 của Chính phủ quy định
               chi tiết và hướng dẫn thi hành một số điều của Luật Lý lịch tư pháp,</p>
          </div>

          <div class="xacnhan">XÁC NHẬN:</div>

          <div class="info">
            <p><span class="n">1.</span> Họ và tên: <b>{{ c.subject.fullName }}</b>
               <span class="g2"><span class="n">2.</span> Giới tính: {{ c.subject.gender }}</span></p>
            <p><span class="n">3.</span> Ngày, tháng, năm sinh: {{ c.subject.dateOfBirth }}</p>
            <p><span class="n">4.</span> Nơi sinh: {{ c.subject.birthPlace || '—' }}</p>
            <p><span class="n">5.</span> Quốc tịch: {{ c.subject.nationality || 'Việt Nam' }}
               @if (c.subject.ethnicity) { <span class="g2">Dân tộc: {{ c.subject.ethnicity }}</span> }</p>
            <p><span class="n">6.</span> Số định danh cá nhân/CMND/Hộ chiếu: {{ c.subject.identifyNo || '—' }}</p>
            <p><span class="n">7.</span> Nơi thường trú: {{ c.subject.residence || '—' }}</p>
            <p><span class="n">8.</span> Tình trạng án tích:
               <b [class.has-record]="c.hasCriminalRecord">{{ c.crimeStatus }}</b></p>
          </div>

          @if (c.judgments.length > 0) {
            @for (j of c.judgments; track j.judgmentNo) {
              <div class="box">
                <p>Bản án số {{ j.judgmentNo }} {{ vnDate(j.judgmentDate) }} của {{ j.court }}</p>
                <p>Tội danh, điều khoản của Bộ luật hình sự được áp dụng:
                   {{ j.crimeNames || '—' }}@if (j.terms) { — {{ j.terms }}}</p>
                <p>Hình phạt chính: {{ j.penaltyMain || '—' }}</p>
                <p>Hình phạt bổ sung: {{ j.penaltyAdd || '—' }}</p>
              </div>
            }
          }

          @if (c.banPositions.length > 0) {
            <div class="info">
              <p><span class="n">9.</span> Thông tin về cấm đảm nhiệm chức vụ, thành lập, quản lý
                 doanh nghiệp, hợp tác xã:</p>
            </div>
            @for (b of c.banPositions; track b.decisionNo) {
              <div class="box">
                <p>Quyết định số {{ b.decisionNo }} {{ vnDate(b.decisionDate) }} của {{ b.court }}</p>
                <p>Chức vụ bị cấm đảm nhiệm: {{ b.banPosition }}</p>
                <p>Thời hạn không được thành lập, quản lý doanh nghiệp, hợp tác xã: {{ b.fromDate || '—' }}</p>
              </div>
            }
          }

          <div class="sign-row">
            <div class="qr-wrap">
              <img class="qr" [src]="qrUrl()" alt="QR Phiếu" />
              <div class="qr-cap">Quét mã QR để xác thực</div>
            </div>
            <div class="sign">
              <div class="i sign-date">{{ headerDate(c) }}</div>
              <div class="b">NGƯỜI CÓ THẨM QUYỀN KÝ</div>
              <div class="i sign-note">(Ký, ghi rõ họ tên, đóng dấu)</div>
              @if (c.digitallySigned) { <div class="signed">🔏 Đã ký số</div> }
              <div class="b sign-name">{{ c.issuePerson || '' }}</div>
            </div>
          </div>
        </div>
      </div>
    }
  `,
  styles: `
    .cert-toolbar { display: flex; align-items: center; gap: 12px; margin-bottom: 14px; flex-wrap: wrap; }
    .badge { display: inline-block; background: #e8f5e9; color: #2e7d32; font-size: 12px; font-weight: 700; padding: 5px 12px; border-radius: 12px; }
    .via { font-size: 12px; color: var(--color-muted); }
    .spacer { flex: 1; }
    .btn-print { background: var(--color-ochre); color: white; padding: 9px 18px; border-radius: 4px; font-weight: 600; font-size: 13px; }
    .btn-print:hover { background: var(--color-ochre-hover); }

    .phieu {
      position: relative;
      width: 760px;
      max-width: 100%;
      min-height: 1075px;
      margin: 0 auto;
      background: #fffdf8;
      box-shadow: 0 4px 18px rgba(0,0,0,0.14);
      -webkit-print-color-adjust: exact;
      print-color-adjust: exact;
      overflow: hidden;
    }
    .phoi-bg { position: absolute; inset: 0; width: 100%; height: 100%; object-fit: fill; z-index: 0; user-select: none; pointer-events: none; }
    .phieu-body {
      position: relative; z-index: 1;
      padding: 70px 74px 56px;
      font-family: 'Times New Roman', 'Tinos', serif;
      font-size: 15px; color: #1a1a1a; line-height: 1.5;
    }

    .mau-so { position: absolute; top: 18px; right: 24px; font-size: 12px; font-style: italic; color: #444; }
    .b { font-weight: 700; }
    .i { font-style: italic; }

    .head { display: flex; justify-content: space-between; gap: 24px; text-align: center; }
    .head-left { width: 46%; }
    .head-right { width: 54%; }
    .head .agency { font-size: 13.5px; line-height: 1.35; text-transform: uppercase; }
    .head-right .b { font-size: 14px; }
    .hr { height: 1px; background: #1a1a1a; margin: 4px auto 0; }
    .hr-left { width: 120px; }
    .hr-right { width: 190px; }

    .subhead { display: flex; justify-content: space-between; align-items: flex-start; margin-top: 6px; font-size: 14px; }

    .title { text-align: center; font-size: 17px; font-weight: 700; margin: 18px 0 14px; letter-spacing: 0.3px; }

    .cancu { font-size: 13.5px; line-height: 1.4; }
    .cancu p { margin: 0 0 6px; text-indent: 28px; }

    .xacnhan { text-align: center; font-weight: 700; font-size: 15px; margin: 12px 0; }

    .info p { margin: 0 0 9px; }
    .info .n { display: inline-block; min-width: 18px; }
    .info .g2 { margin-left: 34px; }
    .has-record { color: #c0392b; }

    .box { border: 1px solid #1a1a1a; padding: 8px 12px; margin: 0 0 10px; background: rgba(255,255,255,0.55); }
    .box p { margin: 0 0 5px; }
    .box p:last-child { margin-bottom: 0; }

    .sign-row { display: flex; justify-content: space-between; align-items: flex-start; margin-top: 28px; gap: 20px; }
    .qr-wrap { text-align: center; }
    .qr { width: 96px; height: 96px; background: white; border: 1px solid #ccc; padding: 3px; }
    .qr-cap { font-size: 11px; font-style: italic; color: #555; margin-top: 4px; }
    .sign { width: 58%; text-align: center; }
    .sign-date { margin-bottom: 4px; }
    .sign-note { font-size: 12.5px; color: #444; }
    .signed { display: inline-block; margin-top: 8px; font-size: 12px; font-weight: 700; color: #1b5e20; }
    .sign-name { margin-top: 70px; }

    @media (max-width: 820px) {
      .phieu { width: 100%; min-height: auto; }
      .phieu-body { padding: 40px 26px; }
    }
  `
})
export class CertificateViewComponent {
  private readonly lltp = inject(LltpService);

  readonly cert = signal<CertificateDto | null>(null);
  @Input({ required: true }) set certificate(v: CertificateDto) { this.cert.set(v); }
  @Input() verifiedVia: string | null = null;

  readonly qrUrl = computed<string>(() => {
    const c = this.cert();
    return c ? this.lltp.certificateQrUrl(c.certificateNumber) : '';
  });

  /** "Ngày D tháng M năm Y" from a dd/MM/yyyy string (falls back to the raw value). */
  vnDate(s: string | null): string {
    if (!s) return '';
    const m = /^(\d{1,2})\/(\d{1,2})\/(\d{4})$/.exec(s.trim());
    return m ? `ngày ${m[1]} tháng ${m[2]} năm ${m[3]}` : s;
  }

  headerDate(c: CertificateDto): string {
    const v = this.vnDate(c.issueDate);
    return v ? v.charAt(0).toUpperCase() + v.slice(1) : '';
  }

  agencyUpper(c: CertificateDto): string {
    return (c.issuingAgency || '').toLocaleUpperCase('vi');
  }

  viaLabel(via: string): string {
    return via === 'QR' ? 'Quét mã QR' : 'Nhập thông tin trên Phiếu';
  }

  print(): void {
    window.print();
  }
}
