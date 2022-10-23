package com.livebarn.sushishop.controller;

import com.livebarn.sushishop.model.Order;
import com.livebarn.sushishop.model.Sushi;
import com.livebarn.sushishop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/orders")
    private List<Order> getOrders() {
        return orderService.getOrders();
    }

    @PostMapping("/orders")
    private int placeOrder(@RequestBody Sushi sushi) {
        System.out.println(sushi);
        orderService.placeOrder(sushi);
        return 0;
    }
}