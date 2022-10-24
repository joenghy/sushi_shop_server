package com.livebarn.sushishop.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ResponseDTO {
    private Integer code;
    @ApiModelProperty(example = "Order cancelled")
    private String msg;
}