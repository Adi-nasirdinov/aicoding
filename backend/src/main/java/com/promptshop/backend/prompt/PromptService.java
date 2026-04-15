package com.promptshop.backend.prompt;

import com.promptshop.backend.common.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;

@Service
public class PromptService {

    private final PromptRepository promptRepository;

    public PromptService(PromptRepository promptRepository) {
        this.promptRepository = promptRepository;
    }

    @Transactional(readOnly = true)
    public List<PromptResponse> listPublicPrompts() {
        return promptRepository.findByPublishedTrueOrderByCreatedAtDesc()
                .stream()
                .map(PromptResponse::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public PromptResponse getPublicPrompt(Long id) {
        Prompt prompt = promptRepository.findByIdAndPublishedTrue(id)
                .orElseThrow(() -> new NotFoundException("Prompt not found"));
        return PromptResponse.fromEntity(prompt);
    }

    @Transactional(readOnly = true)
    public List<PromptResponse> listAllPrompts() {
        return promptRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"))
                .stream()
                .map(PromptResponse::fromEntity)
                .toList();
    }

    @Transactional
    public PromptResponse createPrompt(PromptRequest request) {
        Prompt prompt = new Prompt();
        applyRequest(prompt, request);
        return PromptResponse.fromEntity(promptRepository.save(prompt));
    }

    @Transactional
    public PromptResponse updatePrompt(Long id, PromptRequest request) {
        Prompt prompt = promptRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Prompt not found"));
        applyRequest(prompt, request);
        return PromptResponse.fromEntity(promptRepository.save(prompt));
    }

    @Transactional
    public void deletePrompt(Long id) {
        Prompt prompt = promptRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Prompt not found"));
        promptRepository.delete(prompt);
    }

    @Transactional(readOnly = true)
    public List<Prompt> getPublishedPromptsByIds(List<Long> promptIds) {
        List<Prompt> prompts = promptRepository.findByIdInAndPublishedTrue(promptIds);
        LinkedHashMap<Long, Prompt> byId = new LinkedHashMap<>();
        for (Prompt prompt : prompts) {
            byId.put(prompt.getId(), prompt);
        }

        return promptIds.stream()
                .map(byId::get)
                .peek(prompt -> {
                    if (prompt == null) {
                        throw new NotFoundException("One or more selected prompts do not exist");
                    }
                })
                .toList();
    }

    @Transactional(readOnly = true)
    public long totalPrompts() {
        return promptRepository.count();
    }

    private void applyRequest(Prompt prompt, PromptRequest request) {
        prompt.setTitle(request.title().trim());
        prompt.setShortDescription(request.shortDescription().trim());
        prompt.setContentText(request.contentText().trim());
        prompt.setPriceUsd(request.priceUsd());
        prompt.setCategory(request.category().trim());
        prompt.setTags(request.tags().trim());
        prompt.setImageUrl(request.imageUrl().trim());
        prompt.setPublished(request.published());
    }
}
