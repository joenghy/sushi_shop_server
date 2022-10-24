package com.livebarn.sushishop.model;

import com.livebarn.sushishop.dto.OrderStatusDTO;
import lombok.Data;

@Data
public class OrderInPlace {
    private Integer orderId;
    private Integer timeToMake;
    private Integer timeSpent;
    private Integer statusId;

    public OrderInPlace(Integer orderId, Integer timeToMake, Integer statusId) {
        this.orderId = orderId;
        this.timeToMake = timeToMake;
        this.statusId = statusId;
        this.timeSpent = 0;
    }

    public Integer updateStatus(Integer chefsAvailable) {
        if (statusId.equals(Status.IN_PROGRESS.getStatusId())) {
            this.timeSpent++;
            if (this.timeSpent.equals(this.timeToMake)) {
                setStatusId(Status.FINISHED.getStatusId());
                return 1;
            }
        } else if (statusId.equals(Status.CREATED.getStatusId()) && chefsAvailable > 0) {
            setStatusId(Status.IN_PROGRESS.getStatusId());
            return -1;
        }
        return 0;
    }

    public OrderStatusDTO getOrderStatus() {
        OrderStatusDTO orderStatusDTO = new OrderStatusDTO();
        orderStatusDTO.setOrderId(this.orderId);
        orderStatusDTO.setTimeSpent(this.timeSpent);
        return orderStatusDTO;
    }
}
