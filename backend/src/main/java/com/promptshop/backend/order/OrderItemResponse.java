package com.promptshop.backend.order;

import java.math.BigDecimal;

public record OrderItemResponse(
        Long promptId,
        String promptTitle,
        BigDecimal priceUsd
) {
}
