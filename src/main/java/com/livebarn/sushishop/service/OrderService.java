package com.livebarn.sushishop.service;

import com.livebarn.sushishop.dto.OrderResponseDTO;
import com.livebarn.sushishop.dto.OrderStatusDTO;
import com.livebarn.sushishop.dto.ResponseDTO;
import com.livebarn.sushishop.dto.StatusResponseDTO;
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
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
//            Integer statusId = chefsAvailable > 0 ? 2 : 1;
            order.setSushiId(sushiRepository.findIdByName(sushi.getName()));
            order.setStatusId(1);
            Order savedOrder = orderRepository.save(order);
            OrderInPlace orderInPlace = new OrderInPlace(savedOrder.getId(), sushiRepository.findTTMByName(sushi.getName()), 1);
            orders.put(savedOrder.getId(), orderInPlace);
            System.out.println(orders);
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
            OrderInPlace orderInPlace = orders.get(id);
            if (orderInPlace.getStatusId().equals(4)) {
                throw new Exception("Order is already finished.");
            } else if (orderInPlace.getStatusId().equals(5)) {
                throw new Exception("Order is already cancelled.");
            }
            orderInPlace.setStatusId(5);
            orders.put(id, orderInPlace);
            orderRepository.setStatusById(id, 5);
            dto.setCode(0);
            dto.setMsg("Order cancelled.");
        } catch (Exception e) {
            dto.setCode(-1);
            dto.setMsg("Order cancel failed. " + e.getMessage());
        }
        return dto;
    }

    public StatusResponseDTO getOrderStatus() {
        StatusResponseDTO dto = new StatusResponseDTO();
        try {
            dto.setInProgress(orders.entrySet().stream().filter(i -> i.getValue().getStatusId().equals(2)).map(Map.Entry::getValue).map(OrderInPlace::getOrderStatus).collect(Collectors.toList()));
            dto.setCreated(orders.entrySet().stream().filter(i -> i.getValue().getStatusId().equals(1)).map(Map.Entry::getValue).map(OrderInPlace::getOrderStatus).collect(Collectors.toList()));
            dto.setPaused(orders.entrySet().stream().filter(i -> i.getValue().getStatusId().equals(3)).map(Map.Entry::getValue).map(OrderInPlace::getOrderStatus).collect(Collectors.toList()));
            dto.setCancelled(orders.entrySet().stream().filter(i -> i.getValue().getStatusId().equals(5)).map(Map.Entry::getValue).map(OrderInPlace::getOrderStatus).collect(Collectors.toList()));
            dto.setCompleted(orders.entrySet().stream().filter(i -> i.getValue().getStatusId().equals(4)).map(Map.Entry::getValue).map(OrderInPlace::getOrderStatus).collect(Collectors.toList()));

            // maybe needed?? should be required but not found in sample Response
            dto.setCode(0);
            dto.setMsg("Order in progress: " + dto.getInProgress().size() + " Order created: " + dto.getCreated().size() + " Order paused: " + dto.getPaused().size() + " Order cancelled: " + dto.getCancelled().size() + " Order completed: " + dto.getCompleted().size());
        } catch (Exception e) {
            // maybe needed?? should be required but not found in sample Response
            dto.setCode(-1);
            dto.setMsg("Get order status failed. " + e.getMessage());
        }
        return dto;
    }

    public ResponseDTO pauseOrder(Integer id) {
        ResponseDTO dto = new ResponseDTO();
        try {
            OrderInPlace orderInPlace = orders.get(id);
            Integer statusId = orderInPlace.getStatusId();
            if (!statusId.equals(2)) {
                throw new Exception("Order is not in progress.");
            }
            orderInPlace.setStatusId(3);
            orders.put(id, orderInPlace);
            orderRepository.setStatusById(id, 3);
            dto.setCode(0);
            dto.setMsg("Order paused.");
        } catch (Exception e) {
            dto.setCode(-1);
            dto.setMsg("Order pause failed. " + e.getMessage());
        }
        return dto;
    }

    public ResponseDTO resumeOrder(Integer id) {
        ResponseDTO dto = new ResponseDTO();
        try {
            OrderInPlace orderInPlace = orders.get(id);
            Integer statusId = orderInPlace.getStatusId();
            if (!statusId.equals(3)) {
                throw new Exception("Order is not paused.");
            }
            orderInPlace.setStatusId(1);
            orders.put(id, orderInPlace);
            orderRepository.setStatusById(id, 1);
            dto.setCode(0);
            dto.setMsg("Order resumed.");
        } catch (Exception e) {
            dto.setCode(-1);
            dto.setMsg("Order resume failed. " + e.getMessage());
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
        orders.forEach((key, value) -> {
            Integer update = value.updateStatus(chefsAvailable);
            if (update != 0) {
                orderRepository.setStatusById(value.getOrderId(), value.getStatusId());
                chefsAvailable += update;
            }
        });
        System.out.println(orders);
        System.out.println(chefsAvailable);
        i++;
    }
}
