package com.livebarn.sushishop.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.List;

@Data
@JsonNaming(PropertyNamingStrategy.KebabCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StatusResponseDTO {
    private List<OrderStatusDTO> inProgress;
    private List<OrderStatusDTO> created;
    private List<OrderStatusDTO> paused;
    private List<OrderStatusDTO> cancelled;
    private List<OrderStatusDTO> completed;

    // maybe needed?? should be required but not found in sample Response
    private Integer code;
    private String msg;
}