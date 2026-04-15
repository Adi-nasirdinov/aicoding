package com.promptshop.backend.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(
        Long id,
        OrderStatus status,
        BigDecimal totalUsd,
        LocalDateTime createdAt,
        List<OrderItemResponse> items
) {
}
