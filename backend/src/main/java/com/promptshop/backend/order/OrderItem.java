package com.promptshop.backend.order;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    private ShopOrder order;

    @Column(nullable = false)
    private Long promptId;

    @Column(nullable = false, length = 140)
    private String promptTitle;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String promptContentSnapshot;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal priceUsd;

    public Long getId() {
        return id;
    }

    public ShopOrder getOrder() {
        return order;
    }

    public void setOrder(ShopOrder order) {
        this.order = order;
    }

    public Long getPromptId() {
        return promptId;
    }

    public void setPromptId(Long promptId) {
        this.promptId = promptId;
    }

    public String getPromptTitle() {
        return promptTitle;
    }

    public void setPromptTitle(String promptTitle) {
        this.promptTitle = promptTitle;
    }

    public String getPromptContentSnapshot() {
        return promptContentSnapshot;
    }

    public void setPromptContentSnapshot(String promptContentSnapshot) {
        this.promptContentSnapshot = promptContentSnapshot;
    }

    public BigDecimal getPriceUsd() {
        return priceUsd;
    }

    public void setPriceUsd(BigDecimal priceUsd) {
        this.priceUsd = priceUsd;
    }
}
