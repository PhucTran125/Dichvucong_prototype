import { Routes } from '@angular/router';

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
