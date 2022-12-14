package com.livebarn.sushishop.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.List;

@Data
// The field in response body is in-progress, not inProgress
@JsonNaming(PropertyNamingStrategy.KebabCaseStrategy.class)
public class StatusResponseDTO {
    private List<OrderStatusDTO> inProgress;
    private List<OrderStatusDTO> created;
    private List<OrderStatusDTO> paused;
    private List<OrderStatusDTO> cancelled;
    private List<OrderStatusDTO> completed;

    // maybe needed?? should be required but not found in sample Response
    private Integer code;
    @ApiModelProperty(example = "Order in progress: 0 Order created: 0 Order paused: 0 Order cancelled: 0 Order completed: 0")
    private String msg;
}