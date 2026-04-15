package com.promptshop.backend.order;

import com.promptshop.backend.prompt.PromptService;
import com.promptshop.backend.user.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminOrderController {

    private final OrderService orderService;
    private final UserRepository userRepository;
    private final PromptService promptService;

    public AdminOrderController(OrderService orderService, UserRepository userRepository, PromptService promptService) {
        this.orderService = orderService;
        this.userRepository = userRepository;
        this.promptService = promptService;
    }

    @GetMapping("/orders")
    public List<OrderResponse> listOrders() {
        return orderService.listAllOrders();
    }

    @PatchMapping("/orders/{id}/status")
    public OrderResponse updateStatus(@PathVariable Long id, @RequestParam OrderStatus status) {
        return orderService.updateOrderStatus(id, status);
    }

    @GetMapping("/dashboard")
    public DashboardResponse dashboard() {
        return orderService.dashboard(userRepository.count(), promptService.totalPrompts());
    }
}
