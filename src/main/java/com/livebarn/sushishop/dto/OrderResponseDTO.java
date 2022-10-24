package com.livebarn.sushishop.dto;

import com.livebarn.sushishop.model.Order;
import lombok.Data;

@Data
public class OrderResponseDTO {
    private Order order;
    private Integer code;
    private String msg;
}