package com.promptshop.backend.auth;

public record AuthResponse(
        String token,
        UserSummary user
) {
}
