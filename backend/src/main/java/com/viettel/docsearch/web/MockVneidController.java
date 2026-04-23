package com.viettel.docsearch.web;

import com.viettel.docsearch.domain.User;
import com.viettel.docsearch.service.MockVneidService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/api/auth/mock-vneid")
public class MockVneidController {

    private final MockVneidService vneid;

    public MockVneidController(MockVneidService vneid) {
        this.vneid = vneid;
    }

    @GetMapping("/authorize")
    public String authorize(
            @RequestParam(name = "redirect_uri") String redirectUri,
            @RequestParam(name = "state", required = false) String state,
            Model model) {
        List<User> identities = vneid.listIdentities();
        model.addAttribute("identities", identities);
        model.addAttribute("redirectUri", redirectUri);
        model.addAttribute("state", state == null ? "" : state);
        return "mock-vneid-picker";
    }

    @GetMapping("/callback")
    public void pickUser(
            @RequestParam(name = "user_id") UUID userId,
            @RequestParam(name = "redirect_uri") String redirectUri,
            @RequestParam(name = "state", required = false) String state,
            HttpServletResponse response) throws IOException {
        String code = vneid.issueCode(userId, redirectUri, state);
        StringBuilder url = new StringBuilder(redirectUri);
        url.append(redirectUri.contains("?") ? '&' : '?');
        url.append("code=").append(URLEncoder.encode(code, StandardCharsets.UTF_8));
        if (state != null && !state.isBlank()) {
            url.append("&state=").append(URLEncoder.encode(state, StandardCharsets.UTF_8));
        }
        response.sendRedirect(url.toString());
    }
}
