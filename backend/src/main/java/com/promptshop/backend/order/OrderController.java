package com.promptshop.backend.order;

import com.promptshop.backend.user.AppUser;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/orders/checkout")
    public OrderResponse checkout(
            @AuthenticationPrincipal AppUser user,
            @Valid @RequestBody CheckoutRequest request
    ) {
        return orderService.checkout(user.getId(), request);
    }

    @GetMapping("/orders/my")
    public List<OrderResponse> myOrders(@AuthenticationPrincipal AppUser user) {
        return orderService.listMyOrders(user.getId());
    }

    @GetMapping("/library")
    public List<LibraryItemResponse> library(@AuthenticationPrincipal AppUser user) {
        return orderService.library(user.getId());
    }
}
