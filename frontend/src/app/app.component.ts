import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { HeaderComponent } from './shared/header.component';
import { NavBarComponent } from './shared/nav-bar.component';
import { LoginDialogComponent } from './shared/login-dialog.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, HeaderComponent, NavBarComponent, LoginDialogComponent],
  template: `
    <app-header></app-header>
    <app-nav-bar></app-nav-bar>
    <main class="main-content">
      <router-outlet></router-outlet>
    </main>
    <app-login-dialog></app-login-dialog>
  `,
  styles: `
    .main-content { max-width: 1140px; margin: 0 auto; padding: 32px 16px 64px; }
  `
})
export class AppComponent {}
