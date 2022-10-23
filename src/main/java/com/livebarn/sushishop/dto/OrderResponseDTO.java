package com.livebarn.sushishop.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.livebarn.sushishop.model.Order;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderResponseDTO {
    private Order order;
    private Integer code;
    private String msg;
}
