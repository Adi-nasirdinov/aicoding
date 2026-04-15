package com.promptshop.backend.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record LibraryItemResponse(
        Long orderId,
        Long promptId,
        String promptTitle,
        String promptContent,
        BigDecimal priceUsd,
        LocalDateTime purchasedAt
) {
}
