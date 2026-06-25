import { Routes } from '@angular/router';
import { officerGuard } from './core/officer.guard';

export const routes: Routes = [
  {
    path: '',
    loadComponent: () => import('./pages/home.component').then(m => m.HomeComponent)
  },
  {
    path: 'search',
    loadComponent: () => import('./pages/search.component').then(m => m.SearchComponent)
  },
  {
    path: 'documents/:id',
    loadComponent: () => import('./pages/document-detail.component').then(m => m.DocumentDetailComponent)
  },
  {
    path: 'dang-nhap-can-bo',
    loadComponent: () => import('./pages/officer-login.component').then(m => m.OfficerLoginComponent),
    data: { title: 'Đăng nhập cán bộ' }
  },
  {
    path: 'verify',
    canActivate: [officerGuard],
    loadComponent: () => import('./pages/verification.component').then(m => m.VerificationComponent),
    data: { title: 'Xác thực Phiếu lý lịch tư pháp' }
  },
  {
    path: 'tao-qr-test',
    loadComponent: () => import('./pages/qr-test.component').then(m => m.QrTestComponent),
    data: { title: 'Tạo mã QR (Test)' }
  },
  {
    path: 'payments',
    loadComponent: () => import('./pages/placeholder.component').then(m => m.PlaceholderComponent),
    data: { title: 'Thanh toán trực tuyến' }
  },
  {
    path: 'feedback',
    loadComponent: () => import('./pages/placeholder.component').then(m => m.PlaceholderComponent),
    data: { title: 'Phản ánh kiến nghị' }
  },
  {
    path: 'help',
    loadComponent: () => import('./pages/placeholder.component').then(m => m.PlaceholderComponent),
    data: { title: 'Hỗ trợ' }
  },
  { path: '**', redirectTo: '' }
];
