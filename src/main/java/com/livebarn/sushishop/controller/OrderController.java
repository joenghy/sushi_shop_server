package com.livebarn.sushishop.controller;

import com.livebarn.sushishop.dto.OrderResponseDTO;
import com.livebarn.sushishop.dto.ResponseDTO;
import com.livebarn.sushishop.dto.StatusResponseDTO;
import com.livebarn.sushishop.model.Sushi;
import com.livebarn.sushishop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/orders")
    @ResponseStatus(HttpStatus.CREATED)
    private OrderResponseDTO placeOrder(@RequestBody Sushi sushi) {
        return orderService.placeOrder(sushi);
    }

    @DeleteMapping(path = "/orders/{order_id}")
    @ResponseStatus(HttpStatus.OK)
    private ResponseDTO cancelOrder(@PathVariable("order_id") Integer id) {
        return orderService.cancelOrder(id);
    }

    @GetMapping("/orders/status")
    @ResponseStatus(HttpStatus.OK)
    private StatusResponseDTO getOrderStatus() {
        return orderService.getOrderStatus();
    }

    @PutMapping("/orders/{order_id}/pause")
    @ResponseStatus(HttpStatus.OK)
    private ResponseDTO pauseOrder(@PathVariable("order_id") Integer id) {
        return orderService.pauseOrder(id);
    }

    @PutMapping("orders/{order_id}/resume")
    @ResponseStatus(HttpStatus.OK)
    private ResponseDTO resumeOrder(@PathVariable("order_id") Integer id) {
        return orderService.resumeOrder(id);
    }
}