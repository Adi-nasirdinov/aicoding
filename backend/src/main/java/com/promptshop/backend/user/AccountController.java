package com.promptshop.backend.user;

import com.promptshop.backend.auth.AuthService;
import com.promptshop.backend.auth.UserSummary;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AccountController {

    private final AuthService authService;

    public AccountController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/me")
    public UserSummary currentUser(@AuthenticationPrincipal AppUser user) {
        return authService.toSummary(user);
    }
}
