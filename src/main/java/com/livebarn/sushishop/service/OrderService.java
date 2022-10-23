package com.livebarn.sushishop.service;

import com.livebarn.sushishop.dto.OrderResponseDTO;
import com.livebarn.sushishop.dto.ResponseDTO;
import com.livebarn.sushishop.model.Order;
import com.livebarn.sushishop.model.OrderInPlace;
import com.livebarn.sushishop.model.Sushi;
import com.livebarn.sushishop.repository.OrderRepository;
import com.livebarn.sushishop.repository.SushiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {
    private int i = 0;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    SushiRepository sushiRepository;

    private List<Integer> currentOrders;
    private List<Integer> pendingOrders;
    private List<Integer> finishedOrders;

    private Integer chefsAvailable = 3;

    private Map<Integer, OrderInPlace> orders = new HashMap<>();

    public List<Order> getOrders() {
        List<Order> orders = new ArrayList<>();
        orderRepository.findAll().forEach(order -> orders.add(order));
        return orders;
    }

    public OrderResponseDTO placeOrder(Sushi sushi) {
        OrderResponseDTO dto = new OrderResponseDTO();
        Order order = new Order();
        try {
//            Integer chefsAvailable = 3 - Math.toIntExact(orders.entrySet().stream().filter(i -> i.getValue().getStatusId().equals(2)).count());
            Integer statusId = chefsAvailable > 0 ? 2 : 1;
            order.setSushiId(sushiRepository.findIdByName(sushi.getName()));
            order.setStatusId(statusId);
            Order savedOrder = orderRepository.save(order);
            OrderInPlace orderInPlace = new OrderInPlace(savedOrder.getId(), sushiRepository.findTTMByName(sushi.getName()), statusId);
            orders.put(savedOrder.getId(), orderInPlace);
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

    public ResponseDTO cancelOrder(Integer id) {
        ResponseDTO dto = new ResponseDTO();
        try {
            orderRepository.setStatusById(id, 5);
            dto.setCode(0);
            dto.setMsg("Order cancelled");
        } catch (Exception e) {
            dto.setCode(-1);
            dto.setMsg("Order cancel failed");
        }
        return dto;
    }

    @Scheduled(fixedRate = 1000)
    public void updateOrderStatus() {
        chefsAvailable = 3 - Math.toIntExact(orders.entrySet().stream().filter(i -> i.getValue().getStatusId().equals(2)).count());
        /*finishedOrders = orderRepository.getFinishedOrders();
        finishedOrders.forEach(id -> orderRepository.setStatusById(id, 4));
        currentOrders = orderRepository.getCurrentOrders();
        System.out.println(i + " current " + currentOrders);
        pendingOrders = orderRepository.getPendingOrders(Math.max(3 - currentOrders.size(), 0));
        System.out.println("pending " + pendingOrders);
        pendingOrders.forEach(id -> orderRepository.setStatusById(id, 2));*/
        orders.forEach((key, value) -> chefsAvailable += value.updateStatus(chefsAvailable));
        System.out.println(orders);
        System.out.println(chefsAvailable);
        i++;
    }
}
