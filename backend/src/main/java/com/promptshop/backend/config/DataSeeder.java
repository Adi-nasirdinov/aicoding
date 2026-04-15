package com.promptshop.backend.config;

import com.promptshop.backend.prompt.Prompt;
import com.promptshop.backend.prompt.PromptRepository;
import com.promptshop.backend.user.AppUser;
import com.promptshop.backend.user.UserRepository;
import com.promptshop.backend.user.UserRole;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@ConditionalOnProperty(name = "app.seed.enabled", havingValue = "true", matchIfMissing = true)
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PromptRepository promptRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.seed.admin-email}")
    private String adminEmail;

    @Value("${app.seed.admin-password}")
    private String adminPassword;

    @Value("${app.seed.customer-email}")
    private String customerEmail;

    @Value("${app.seed.customer-password}")
    private String customerPassword;

    public DataSeeder(
            UserRepository userRepository,
            PromptRepository promptRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.promptRepository = promptRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        seedUsers();
        seedPrompts();
    }

    private void seedUsers() {
        if (userRepository.existsByEmail(adminEmail.toLowerCase())) {
            return;
        }

        AppUser admin = new AppUser();
        admin.setEmail(adminEmail);
        admin.setDisplayName("Prompt Shop Admin");
        admin.setPassword(passwordEncoder.encode(adminPassword));
        admin.setRole(UserRole.ADMIN);

        AppUser customer = new AppUser();
        customer.setEmail(customerEmail);
        customer.setDisplayName("Demo Customer");
        customer.setPassword(passwordEncoder.encode(customerPassword));
        customer.setRole(UserRole.CUSTOMER);

        userRepository.saveAll(List.of(admin, customer));
    }

    private void seedPrompts() {
        if (promptRepository.count() > 0) {
            return;
        }

        promptRepository.saveAll(List.of(
                prompt(
                        "Founder Storytelling OS",
                        "Narrative prompt system for product launches and landing pages.",
                        "You are a brand narrative strategist. Build a launch story with a bold opener, credibility bridge, user pain, vivid future state, and CTA variants.",
                        "Marketing",
                        "launch,storytelling,copywriting",
                        "https://images.unsplash.com/photo-1504805572947-34fad45aed93?auto=format&fit=crop&w=1200&q=80",
                        new BigDecimal("39.00")
                ),
                prompt(
                        "Cold Outreach Engine",
                        "High-converting outbound prompts for founders and sales teams.",
                        "Generate 5 concise outbound messages for LinkedIn and email using pain-angle personalization, social proof, and a low-friction CTA.",
                        "Sales",
                        "outreach,b2b,leadgen",
                        "https://images.unsplash.com/photo-1556155092-490a1ba16284?auto=format&fit=crop&w=1200&q=80",
                        new BigDecimal("29.00")
                ),
                prompt(
                        "UX Audit Copilot",
                        "Prompt template for design critique and conversion-focused UX reviews.",
                        "Act as a senior product designer. Review this UI for hierarchy, spacing, copy clarity, and trust signals. Return severity-ranked issues and fixes.",
                        "Design",
                        "ux,ui,conversion",
                        "https://images.unsplash.com/photo-1559028012-481c04fa702d?auto=format&fit=crop&w=1200&q=80",
                        new BigDecimal("34.00")
                ),
                prompt(
                        "Bug Triage Commander",
                        "Engineering triage prompts to isolate root causes faster.",
                        "Given stack traces and behavior notes, produce root-cause hypotheses, repro checklist, instrumentation plan, and fix-risk assessment.",
                        "Engineering",
                        "debugging,incident,qa",
                        "https://images.unsplash.com/photo-1518773553398-650c184e0bb3?auto=format&fit=crop&w=1200&q=80",
                        new BigDecimal("44.00")
                ),
                prompt(
                        "Interview Intelligence Pack",
                        "Research and interview prompts for hiring loops and candidate screens.",
                        "Build role-specific interview scripts with calibrated follow-ups, rubric anchors, anti-bias reminders, and decision memo format.",
                        "Hiring",
                        "recruiting,interview,hr",
                        "https://images.unsplash.com/photo-1521737604893-d14cc237f11d?auto=format&fit=crop&w=1200&q=80",
                        new BigDecimal("24.00")
                ),
                prompt(
                        "Creator Content System",
                        "Reusable prompt stack for social content production.",
                        "Turn one core idea into a 7-day content plan across X, LinkedIn, and newsletter snippets with platform-native hooks.",
                        "Content",
                        "creator,social,newsletter",
                        "https://images.unsplash.com/photo-1529070538774-1843cb3265df?auto=format&fit=crop&w=1200&q=80",
                        new BigDecimal("19.00")
                )
        ));
    }

    private Prompt prompt(
            String title,
            String shortDescription,
            String contentText,
            String category,
            String tags,
            String imageUrl,
            BigDecimal priceUsd
    ) {
        Prompt prompt = new Prompt();
        prompt.setTitle(title);
        prompt.setShortDescription(shortDescription);
        prompt.setContentText(contentText);
        prompt.setCategory(category);
        prompt.setTags(tags);
        prompt.setImageUrl(imageUrl);
        prompt.setPriceUsd(priceUsd);
        prompt.setPublished(true);
        return prompt;
    }
}
