package com.livebarn.sushishop.controller;

import com.livebarn.sushishop.dto.OrderResponseDTO;
import com.livebarn.sushishop.dto.ResponseDTO;
import com.livebarn.sushishop.model.Order;
import com.livebarn.sushishop.model.Sushi;
import com.livebarn.sushishop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    // for debugging purpose only
    @GetMapping("/orders")
    private List<Order> getOrders() {
        return orderService.getOrders();
    }

    @PostMapping("/orders")
    @ResponseStatus(HttpStatus.CREATED)
    private OrderResponseDTO placeOrder(@RequestBody Sushi sushi) {
        return orderService.placeOrder(sushi);
    }

    @DeleteMapping(path = "/orders/{order_id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO cancelOrder(@PathVariable("order_id") Integer id) {
        return orderService.cancelOrder(id);
    }
}