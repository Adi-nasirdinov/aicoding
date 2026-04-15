package com.promptshop.backend.prompt;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/prompts")
public class PromptController {

    private final PromptService promptService;

    public PromptController(PromptService promptService) {
        this.promptService = promptService;
    }

    @GetMapping
    public List<PromptResponse> listPublicPrompts() {
        return promptService.listPublicPrompts();
    }

    @GetMapping("/{id}")
    public PromptResponse getPublicPrompt(@PathVariable Long id) {
        return promptService.getPublicPrompt(id);
    }
}
