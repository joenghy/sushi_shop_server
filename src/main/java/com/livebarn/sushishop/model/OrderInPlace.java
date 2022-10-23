package com.livebarn.sushishop.model;

import lombok.Data;

@Data
public class OrderInPlace {
    private Integer orderId;
    private Integer timeToMake;
    private Integer timeSpent = 0;
    private Integer statusId;

    public OrderInPlace(Integer orderId, Integer timeToMake, Integer statusId) {
        this.orderId = orderId;
        this.timeToMake = timeToMake;
        this.statusId = statusId;
    }

    public Integer updateStatus(Integer chefsAvailable) {
        if (statusId.equals(2)) {
            timeSpent++;
            if (timeSpent == timeToMake) {
                setStatusId(4);
                return 1;
            }
        } else if (statusId.equals(1) && chefsAvailable > 0) {
            setStatusId(2);
            return -1;
        }
        return 0;
    }
}
