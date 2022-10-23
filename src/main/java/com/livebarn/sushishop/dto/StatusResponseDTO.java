package com.livebarn.sushishop.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StatusResponseDTO {
    private List<OrderStatusDTO> inProgressOrders;
    private List<OrderStatusDTO> createdOrders;
    private List<OrderStatusDTO> pausedOrders;
    private List<OrderStatusDTO> cancelledOrders;
    private List<OrderStatusDTO> completedOrders;
}