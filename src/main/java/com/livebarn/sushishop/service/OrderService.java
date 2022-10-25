package com.livebarn.sushishop.service;

import com.livebarn.sushishop.dto.OrderResponseDTO;
import com.livebarn.sushishop.dto.ResponseDTO;
import com.livebarn.sushishop.dto.StatusResponseDTO;
import com.livebarn.sushishop.model.Order;
import com.livebarn.sushishop.model.OrderInPlace;
import com.livebarn.sushishop.model.Status;
import com.livebarn.sushishop.model.Sushi;
import com.livebarn.sushishop.repository.OrderRepository;
import com.livebarn.sushishop.repository.SushiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    SushiRepository sushiRepository;

    enum StatusCode {
        SUCCESS,
        FAILURE
    }

    private static final Integer NUMBEROFCHEFS = 3;
    private Integer chefsAvailable = NUMBEROFCHEFS;

    private Map<Integer, OrderInPlace> orders = new HashMap<>();

    public OrderResponseDTO placeOrder(Sushi sushi) {
        OrderResponseDTO dto = new OrderResponseDTO();
        Order order = new Order();
        try {
            order.setSushiId(sushiRepository.findIdByName(sushi.getName()));
            order.setStatusId(Status.CREATED.getStatusId());
            // update database
            Order savedOrder = orderRepository.save(order);
            OrderInPlace orderInPlace = new OrderInPlace(savedOrder.getId(), sushiRepository.findTTMByName(sushi.getName()), Status.CREATED.getStatusId());
            orders.put(savedOrder.getId(), orderInPlace);
            dto.setOrder(savedOrder);
            dto.setCode(StatusCode.SUCCESS.ordinal());
            dto.setMsg("Order created. ");
        } catch (Exception e) {
            dto.setOrder(order);
            dto.setCode(StatusCode.FAILURE.ordinal());
            dto.setMsg("Order failed. " + e.getMessage());
        }
        return dto;
    }

    public ResponseDTO cancelOrder(Integer id) {
        ResponseDTO dto = new ResponseDTO();
        try {
            OrderInPlace orderInPlace = orders.get(id);
            if (orderInPlace == null) {
                throw new Exception("Order does not exist.");
            }
            if (orderInPlace.getStatusId().equals(Status.FINISHED.getStatusId())) {
                throw new Exception("Order is already finished.");
            }
            if (orderInPlace.getStatusId().equals(Status.CANCELLED.getStatusId())) {
                throw new Exception("Order is already cancelled.");
            }
            orderInPlace.setStatusId(Status.CANCELLED.getStatusId());
            orders.put(id, orderInPlace);
            // update database
            orderRepository.setStatusById(id, Status.CANCELLED.getStatusId());
            dto.setCode(StatusCode.SUCCESS.ordinal());
            dto.setMsg("Order cancelled.");
        } catch (Exception e) {
            dto.setCode(StatusCode.FAILURE.ordinal());
            dto.setMsg("Order cancel failed. " + e.getMessage());
        }
        return dto;
    }

    public StatusResponseDTO getOrderStatus() {
        StatusResponseDTO dto = new StatusResponseDTO();
        try {
            dto.setInProgress(orders.entrySet().stream()
                    .filter(i -> i.getValue().getStatusId().equals(Status.IN_PROGRESS.getStatusId()))
                    .map(Map.Entry::getValue)
                    .map(OrderInPlace::getOrderStatus)
                    .collect(Collectors.toList()));
            dto.setCreated(orders.entrySet().stream()
                    .filter(i -> i.getValue().getStatusId().equals(Status.CREATED.getStatusId()))
                    .map(Map.Entry::getValue)
                    .map(OrderInPlace::getOrderStatus)
                    .collect(Collectors.toList()));
            dto.setPaused(orders.entrySet().stream()
                    .filter(i -> i.getValue().getStatusId().equals(Status.PAUSED.getStatusId()))
                    .map(Map.Entry::getValue)
                    .map(OrderInPlace::getOrderStatus)
                    .collect(Collectors.toList()));
            dto.setCancelled(orders.entrySet().stream()
                    .filter(i -> i.getValue().getStatusId().equals(Status.CANCELLED.getStatusId()))
                    .map(Map.Entry::getValue)
                    .map(OrderInPlace::getOrderStatus)
                    .collect(Collectors.toList()));
            dto.setCompleted(orders.entrySet().stream()
                    .filter(i -> i.getValue().getStatusId().equals(Status.FINISHED.getStatusId()))
                    .map(Map.Entry::getValue)
                    .map(OrderInPlace::getOrderStatus)
                    .collect(Collectors.toList()));

            // maybe needed?? should be required but not found in sample Response
            dto.setCode(StatusCode.SUCCESS.ordinal());
            dto.setMsg("Order in progress: " + dto.getInProgress().size() + " Order created: " + dto.getCreated().size() + " Order paused: " + dto.getPaused().size() + " Order cancelled: " + dto.getCancelled().size() + " Order completed: " + dto.getCompleted().size());
        } catch (Exception e) {
            // maybe needed?? should be required but not found in sample Response
            dto.setCode(StatusCode.FAILURE.ordinal());
            dto.setMsg("Get order status failed. " + e.getMessage());
        }
        return dto;
    }

    public ResponseDTO pauseOrder(Integer id) {
        ResponseDTO dto = new ResponseDTO();
        try {
            OrderInPlace orderInPlace = orders.get(id);
            if (orderInPlace == null) {
                throw new Exception("Order does not exist.");
            }
            Integer statusId = orderInPlace.getStatusId();
            if (!statusId.equals(Status.IN_PROGRESS.getStatusId())) {
                throw new Exception("Order is not in progress.");
            }
            orderInPlace.setStatusId(Status.PAUSED.getStatusId());
            orders.put(id, orderInPlace);
            // update database
            orderRepository.setStatusById(id, Status.PAUSED.getStatusId());
            dto.setCode(StatusCode.SUCCESS.ordinal());
            dto.setMsg("Order paused.");
        } catch (Exception e) {
            dto.setCode(StatusCode.FAILURE.ordinal());
            dto.setMsg("Order pause failed. " + e.getMessage());
        }
        return dto;
    }

    public ResponseDTO resumeOrder(Integer id) {
        ResponseDTO dto = new ResponseDTO();
        try {
            OrderInPlace orderInPlace = orders.get(id);
            if (orderInPlace == null) {
                throw new Exception("Order does not exist.");
            }
            Integer statusId = orderInPlace.getStatusId();
            if (!statusId.equals(Status.PAUSED.getStatusId())) {
                throw new Exception("Order is not paused.");
            }
            orderInPlace.setStatusId(Status.CREATED.getStatusId());
            orders.put(id, orderInPlace);
            orderRepository.setStatusById(id, Status.CREATED.getStatusId());
            dto.setCode(StatusCode.SUCCESS.ordinal());
            dto.setMsg("Order resumed.");
        } catch (Exception e) {
            dto.setCode(StatusCode.FAILURE.ordinal());
            dto.setMsg("Order resume failed. " + e.getMessage());
        }
        return dto;
    }

    // update status every second
    @Scheduled(fixedRate = 1000)
    public void updateOrderStatus() {
        try {
            chefsAvailable = NUMBEROFCHEFS - Math.toIntExact(orders.entrySet().stream()
                    .filter(i -> i.getValue().getStatusId().equals(Status.IN_PROGRESS.getStatusId()))
                    .count());
            orders.forEach((key, value) -> {
                Integer update = value.updateStatus(chefsAvailable);
                if (update != 0) {
                    // update database
                    orderRepository.setStatusById(value.getOrderId(), value.getStatusId());
                    chefsAvailable += update;
                }
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}