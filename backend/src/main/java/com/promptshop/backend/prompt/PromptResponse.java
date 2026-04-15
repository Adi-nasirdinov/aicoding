package com.promptshop.backend.prompt;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PromptResponse(
        Long id,
        String title,
        String shortDescription,
        String contentText,
        BigDecimal priceUsd,
        String category,
        String tags,
        String imageUrl,
        boolean published,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static PromptResponse fromEntity(Prompt prompt) {
        return new PromptResponse(
                prompt.getId(),
                prompt.getTitle(),
                prompt.getShortDescription(),
                prompt.getContentText(),
                prompt.getPriceUsd(),
                prompt.getCategory(),
                prompt.getTags(),
                prompt.getImageUrl(),
                prompt.isPublished(),
                prompt.getCreatedAt(),
                prompt.getUpdatedAt()
        );
    }
}
