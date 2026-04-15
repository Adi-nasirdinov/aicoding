package com.promptshop.backend.auth;

import com.promptshop.backend.user.UserRole;

public record UserSummary(
        Long id,
        String email,
        String displayName,
        UserRole role
) {
}
