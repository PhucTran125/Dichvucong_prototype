package com.viettel.docsearch.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Forwards Angular client-side routes to index.html so the SPA can handle
 * them after a full-page load (e.g. scanning a QR code that points at
 * "/search?id=..." or pasting "/documents/:id" directly). Static assets
 * (*.js, *.css, *.png, etc.) are served by Spring Boot's default resource
 * handler from classpath:/static/ and do NOT match these routes.
 *
 * API endpoints start with /api/ and the H2 console lives at /h2-console —
 * neither is listed here, so those routes are unaffected.
 */
@Controller
public class SpaController {

    @GetMapping({
        "/search",
        "/documents/{id}",
        "/payments",
        "/feedback",
        "/help"
    })
    public String forwardToSpa() {
        return "forward:/index.html";
    }
}
