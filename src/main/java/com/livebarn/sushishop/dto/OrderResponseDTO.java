package com.livebarn.sushishop.dto;

import com.livebarn.sushishop.model.Order;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OrderResponseDTO {
    private Order order;
    private Integer code;
    @ApiModelProperty(example = "Order created")
    private String msg;
}