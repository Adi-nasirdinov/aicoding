package com.promptshop.backend.order;

import java.math.BigDecimal;

public record DashboardResponse(
        long totalUsers,
        long totalPrompts,
        long totalOrders,
        BigDecimal totalRevenueUsd
) {
}
