package com.promptshop.backend.prompt;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record PromptRequest(
        @NotBlank @Size(max = 140) String title,
        @NotBlank @Size(max = 500) String shortDescription,
        @NotBlank String contentText,
        @NotNull @DecimalMin("0.00") BigDecimal priceUsd,
        @NotBlank @Size(max = 90) String category,
        @NotBlank @Size(max = 220) String tags,
        @NotBlank @Size(max = 500) String imageUrl,
        boolean published
) {
}
