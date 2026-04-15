package com.promptshop.backend.order;

import com.promptshop.backend.common.NotFoundException;
import com.promptshop.backend.prompt.Prompt;
import com.promptshop.backend.prompt.PromptService;
import com.promptshop.backend.user.AppUser;
import com.promptshop.backend.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final PromptService promptService;

    public OrderService(OrderRepository orderRepository, UserRepository userRepository, PromptService promptService) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.promptService = promptService;
    }

    @Transactional
    public OrderResponse checkout(Long userId, CheckoutRequest request) {
        if (request.promptIds() == null || request.promptIds().isEmpty()) {
            throw new IllegalArgumentException("At least one prompt is required for checkout");
        }

        List<Long> promptIds = request.promptIds().stream().distinct().toList();
        AppUser customer = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));
        List<Prompt> prompts = promptService.getPublishedPromptsByIds(promptIds);

        BigDecimal total = prompts.stream()
                .map(Prompt::getPriceUsd)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        ShopOrder order = new ShopOrder();
        order.setCustomer(customer);
        order.setStatus(OrderStatus.PAID);
        order.setTotalUsd(total);

        List<OrderItem> orderItems = prompts.stream().map(prompt -> {
            OrderItem item = new OrderItem();
            item.setPromptId(prompt.getId());
            item.setPromptTitle(prompt.getTitle());
            item.setPromptContentSnapshot(prompt.getContentText());
            item.setPriceUsd(prompt.getPriceUsd());
            return item;
        }).toList();
        order.setItems(orderItems);

        ShopOrder savedOrder = orderRepository.save(order);
        return toOrderResponse(savedOrder);
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> listMyOrders(Long userId) {
        return orderRepository.findByCustomerIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(this::toOrderResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<LibraryItemResponse> library(Long userId) {
        List<ShopOrder> paidOrders = orderRepository.findByCustomerIdAndStatusOrderByCreatedAtDesc(
                userId,
                OrderStatus.PAID
        );
        Map<Long, LibraryItemResponse> latestByPrompt = new LinkedHashMap<>();

        for (ShopOrder order : paidOrders) {
            for (OrderItem item : order.getItems()) {
                latestByPrompt.putIfAbsent(
                        item.getPromptId(),
                        new LibraryItemResponse(
                                order.getId(),
                                item.getPromptId(),
                                item.getPromptTitle(),
                                item.getPromptContentSnapshot(),
                                item.getPriceUsd(),
                                order.getCreatedAt()
                        )
                );
            }
        }
        return new ArrayList<>(latestByPrompt.values());
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> listAllOrders() {
        return orderRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(this::toOrderResponse)
                .toList();
    }

    @Transactional
    public OrderResponse updateOrderStatus(Long orderId, OrderStatus status) {
        ShopOrder order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order not found"));
        order.setStatus(status);
        return toOrderResponse(orderRepository.save(order));
    }

    @Transactional(readOnly = true)
    public DashboardResponse dashboard(long totalUsers, long totalPrompts) {
        long totalOrders = orderRepository.count();
        BigDecimal totalRevenue = orderRepository.totalRevenue();
        return new DashboardResponse(totalUsers, totalPrompts, totalOrders, totalRevenue);
    }

    private OrderResponse toOrderResponse(ShopOrder order) {
        List<OrderItemResponse> items = order.getItems().stream()
                .map(item -> new OrderItemResponse(item.getPromptId(), item.getPromptTitle(), item.getPriceUsd()))
                .toList();
        return new OrderResponse(order.getId(), order.getStatus(), order.getTotalUsd(), order.getCreatedAt(), items);
    }
}
