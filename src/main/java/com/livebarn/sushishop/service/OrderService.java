package com.livebarn.sushishop.service;

import com.livebarn.sushishop.model.Order;
import com.livebarn.sushishop.model.Sushi;
import com.livebarn.sushishop.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;

    public List<Order> getOrders() {
        List<Order> orders = new ArrayList<>();
        orderRepository.findAll().forEach(order -> orders.add(order));
        return orders;
    }

    public void placeOrder(Sushi sushi) {
        Order order = new Order();
        order.setStatus_id(1);
        order.setSushi_id(1);
        orderRepository.save(order);
    }
}
