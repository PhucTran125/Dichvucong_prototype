import { Injectable, computed, inject, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { firstValueFrom } from 'rxjs';
import { API_BASE } from './api';
import { TokenResponse, UserDto } from './models';

// Persist to sessionStorage so a login survives the full-page navigation of the
// mock-VNeID redirect flow (and ordinary refreshes) within the tab.
const STORAGE_KEY = 'dvc.auth';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private readonly http = inject(HttpClient);
  private readonly tokenSig = signal<string | null>(null);
  private readonly userSig = signal<UserDto | null>(null);

  readonly token = this.tokenSig.asReadonly();
  readonly user = this.userSig.asReadonly();
  readonly isAuthenticated = computed(() => this.tokenSig() !== null);
  readonly isOrganization = computed(() => this.userSig()?.accountType === 'ORG');
  readonly isOfficer = computed(() => this.userSig()?.accountType === 'OFFICER');

  constructor() {
    this.restore();
  }

  async passwordLogin(citizenId: string, password: string): Promise<TokenResponse> {
    const res = await firstValueFrom(
      this.http.post<TokenResponse>(`${API_BASE}/api/auth/password-login`, { citizenId, password })
    );
    this.apply(res);
    return res;
  }

  /** Mock cán bộ (official) login — preset username/password gating the LLTP verify page. */
  async officerLogin(username: string, password: string): Promise<TokenResponse> {
    const res = await firstValueFrom(
      this.http.post<TokenResponse>(`${API_BASE}/api/auth/officer-login`, { username, password })
    );
    this.apply(res);
    return res;
  }

  /** Exchange a mock-VNeID authorization code (returned to the redirect URI) for a JWT. */
  async exchangeMockVneidCode(code: string): Promise<TokenResponse> {
    const res = await firstValueFrom(
      this.http.post<TokenResponse>(`${API_BASE}/api/auth/mock-vneid/token`, { code })
    );
    this.apply(res);
    return res;
  }

  /** Redirect the browser to the mock-VNeID identity picker, returning to {@code returnUrl} after. */
  loginWithMockVneid(returnUrl: string): void {
    const url = `${API_BASE}/api/auth/mock-vneid/authorize`
      + `?redirect_uri=${encodeURIComponent(returnUrl)}&state=lltp`;
    window.location.assign(url);
  }

  logout(): void {
    this.tokenSig.set(null);
    this.userSig.set(null);
    try { sessionStorage.removeItem(STORAGE_KEY); } catch { /* ignore */ }
  }

  private apply(res: TokenResponse): void {
    this.tokenSig.set(res.accessToken);
    this.userSig.set(res.user);
    try {
      sessionStorage.setItem(STORAGE_KEY, JSON.stringify({ token: res.accessToken, user: res.user }));
    } catch { /* storage unavailable — stay in-memory */ }
  }

  private restore(): void {
    try {
      const raw = sessionStorage.getItem(STORAGE_KEY);
      if (!raw) return;
      const parsed = JSON.parse(raw) as { token: string; user: UserDto };
      if (parsed?.token) {
        this.tokenSig.set(parsed.token);
        this.userSig.set(parsed.user ?? null);
      }
    } catch { /* ignore malformed storage */ }
  }
}
