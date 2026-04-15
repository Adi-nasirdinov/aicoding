package com.promptshop.backend.order;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface OrderRepository extends JpaRepository<ShopOrder, Long> {

    @EntityGraph(attributePaths = {"items"})
    List<ShopOrder> findByCustomerIdOrderByCreatedAtDesc(Long customerId);

    @EntityGraph(attributePaths = {"items"})
    List<ShopOrder> findByCustomerIdAndStatusOrderByCreatedAtDesc(Long customerId, OrderStatus status);

    @EntityGraph(attributePaths = {"items"})
    List<ShopOrder> findAllByOrderByCreatedAtDesc();

    @Query("select coalesce(sum(o.totalUsd), 0) from ShopOrder o where o.status = com.promptshop.backend.order.OrderStatus.PAID")
    BigDecimal totalRevenue();
}
