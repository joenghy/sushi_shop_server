package com.livebarn.sushishop.service;

import com.livebarn.sushishop.dto.OrderResponseDTO;
import com.livebarn.sushishop.model.Order;
import com.livebarn.sushishop.model.Sushi;
import com.livebarn.sushishop.repository.OrderRepository;
import com.livebarn.sushishop.repository.SushiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    SushiRepository sushiRepository;

    private List<Integer> currentOrders;
    private List<Integer> pendingOrders;
    private List<Integer> finishedOrders;

    public List<Order> getOrders() {
        List<Order> orders = new ArrayList<>();
        orderRepository.findAll().forEach(order -> orders.add(order));
        return orders;
    }

    public OrderResponseDTO placeOrder(Sushi sushi) {
        OrderResponseDTO dto = new OrderResponseDTO();
        Order order = new Order();
        try {
            order.setSushiId(sushiRepository.findIdByName(sushi.getName()));
            order.setStatusId(currentOrders.size() < 3 ? 1 : 4);
            Order savedOrder = orderRepository.save(order);
            dto.setOrder(savedOrder);
            dto.setCode(0);
            dto.setMsg("Order created");
        } catch (Exception e) {
            dto.setOrder(order);
            dto.setCode(-1);
            dto.setMsg("Order failed");
        }
        return dto;
    }

    // check if any order finished
    @Scheduled(fixedRate = 1000)
    public void updateOrderStatus() {
        finishedOrders = orderRepository.getFinishedOrders();
        finishedOrders.forEach(id -> orderRepository.setStatusById(id, 4));
        currentOrders = orderRepository.getCurrentOrders();
        System.out.println("current " + currentOrders);
        pendingOrders = orderRepository.getPendingOrders(Math.max(3 - currentOrders.size(), 0));
        System.out.println("pending " + pendingOrders);
        pendingOrders.forEach(id -> orderRepository.setStatusById(id, 2));
    }
}
