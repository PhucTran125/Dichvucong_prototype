import { Injectable, signal } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class LoginDialogService {
  private readonly openSig = signal<boolean>(false);
  private readonly prefilledSig = signal<string | null>(null);

  readonly isOpen = this.openSig.asReadonly();
  readonly prefilledCitizenId = this.prefilledSig.asReadonly();

  open(prefilledCitizenId?: string | null): void {
    this.prefilledSig.set(prefilledCitizenId ?? null);
    this.openSig.set(true);
  }

  close(): void {
    this.openSig.set(false);
    this.prefilledSig.set(null);
  }
}
