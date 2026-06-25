import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from './auth.service';

/**
 * Gates the LLTP verification page behind a cán bộ (officer) login. When not logged in,
 * redirects to the officer login page carrying the attempted URL as {@code returnUrl} so the
 * QR's {@code ?doc=} survives the round-trip and pre-fills the form afterwards.
 */
export const officerGuard: CanActivateFn = (_route, state) => {
  const auth = inject(AuthService);
  const router = inject(Router);
  if (auth.isOfficer()) return true;
  return router.createUrlTree(['/dang-nhap-can-bo'], { queryParams: { returnUrl: state.url } });
};
