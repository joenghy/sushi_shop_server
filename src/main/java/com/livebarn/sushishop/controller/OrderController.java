package com.livebarn.sushishop.controller;

import com.livebarn.sushishop.dto.OrderResponseDTO;
import com.livebarn.sushishop.dto.ResponseDTO;
import com.livebarn.sushishop.dto.StatusResponseDTO;
import com.livebarn.sushishop.model.Sushi;
import com.livebarn.sushishop.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
@Api(value = "Order Management", protocols = "http")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @ApiOperation(value = "To post an order by sushi name", response = OrderResponseDTO.class, code = 201)
    @PostMapping("/orders")
    @ResponseStatus(HttpStatus.CREATED)
    private OrderResponseDTO placeOrder(@RequestBody Sushi sushi) {
        return orderService.placeOrder(sushi);
    }

    @ApiOperation(value = "To cancel an order by order id", response = ResponseDTO.class, code = 200)
    @DeleteMapping(path = "/orders/{order_id}")
    @ResponseStatus(HttpStatus.OK)
    private ResponseDTO cancelOrder(@PathVariable("order_id") Integer id) {
        return orderService.cancelOrder(id);
    }

    @ApiOperation(value = "To get status of all orders", response = StatusResponseDTO.class, code = 200)
    @GetMapping("/orders/status")
    @ResponseStatus(HttpStatus.OK)
    private StatusResponseDTO getOrderStatus() {
        return orderService.getOrderStatus();
    }

    @ApiOperation(value = "To pause an order by id", response = ResponseDTO.class, code = 200)
    @PutMapping("/orders/{order_id}/pause")
    @ResponseStatus(HttpStatus.OK)
    private ResponseDTO pauseOrder(@PathVariable("order_id") Integer id) {
        return orderService.pauseOrder(id);
    }

    @ApiOperation(value = "To resume an order by id", response = ResponseDTO.class, code = 200)
    @PutMapping("orders/{order_id}/resume")
    @ResponseStatus(HttpStatus.OK)
    private ResponseDTO resumeOrder(@PathVariable("order_id") Integer id) {
        return orderService.resumeOrder(id);
    }
}