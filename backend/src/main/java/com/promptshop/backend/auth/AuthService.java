package com.promptshop.backend.auth;

import com.promptshop.backend.common.ConflictException;
import com.promptshop.backend.common.NotFoundException;
import com.promptshop.backend.user.AppUser;
import com.promptshop.backend.user.UserRepository;
import com.promptshop.backend.user.UserRole;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            JwtService jwtService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        String normalizedEmail = request.email().trim().toLowerCase();
        if (userRepository.existsByEmail(normalizedEmail)) {
            throw new ConflictException("Email is already registered");
        }

        AppUser user = new AppUser();
        user.setEmail(normalizedEmail);
        user.setDisplayName(request.displayName().trim());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole(UserRole.CUSTOMER);

        AppUser savedUser = userRepository.save(user);
        String token = jwtService.generateToken(savedUser.getId(), savedUser.getRole().name(), savedUser);
        return new AuthResponse(token, toSummary(savedUser));
    }

    public AuthResponse login(AuthRequest request) {
        String normalizedEmail = request.email().trim().toLowerCase();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                normalizedEmail,
                request.password()
        ));

        AppUser user = userRepository.findByEmail(normalizedEmail)
                .orElseThrow(() -> new NotFoundException("User not found"));
        String token = jwtService.generateToken(user.getId(), user.getRole().name(), user);
        return new AuthResponse(token, toSummary(user));
    }

    public UserSummary toSummary(AppUser user) {
        return new UserSummary(user.getId(), user.getEmail(), user.getDisplayName(), user.getRole());
    }
}
