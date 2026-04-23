import { Injectable, computed, inject, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { firstValueFrom } from 'rxjs';
import { API_BASE } from './api';
import { TokenResponse, UserDto } from './models';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private readonly http = inject(HttpClient);
  private readonly tokenSig = signal<string | null>(null);
  private readonly userSig = signal<UserDto | null>(null);

  readonly token = this.tokenSig.asReadonly();
  readonly user = this.userSig.asReadonly();
  readonly isAuthenticated = computed(() => this.tokenSig() !== null);

  async passwordLogin(citizenId: string, password: string): Promise<TokenResponse> {
    const res = await firstValueFrom(
      this.http.post<TokenResponse>(`${API_BASE}/api/auth/password-login`, { citizenId, password })
    );
    this.tokenSig.set(res.accessToken);
    this.userSig.set(res.user);
    return res;
  }

  logout(): void {
    this.tokenSig.set(null);
    this.userSig.set(null);
  }
}
