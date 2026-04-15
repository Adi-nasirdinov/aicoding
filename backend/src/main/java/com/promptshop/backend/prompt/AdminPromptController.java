package com.promptshop.backend.prompt;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/prompts")
public class AdminPromptController {

    private final PromptService promptService;

    public AdminPromptController(PromptService promptService) {
        this.promptService = promptService;
    }

    @GetMapping
    public List<PromptResponse> listAll() {
        return promptService.listAllPrompts();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PromptResponse create(@Valid @RequestBody PromptRequest request) {
        return promptService.createPrompt(request);
    }

    @PutMapping("/{id}")
    public PromptResponse update(@PathVariable Long id, @Valid @RequestBody PromptRequest request) {
        return promptService.updatePrompt(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        promptService.deletePrompt(id);
    }
}
