package com.livebarn.sushishop.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "sushi_order")
public class Order {
    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    @ApiModelProperty(example = "10")
    private Integer id;
    @ApiModelProperty(example = "1")
    private Integer statusId;
    @ApiModelProperty(example = "1")
    private Integer sushiId;
    @ApiModelProperty(dataType = "date-time", example = "1582643059540")
    @CreationTimestamp
    private Timestamp createdAt;

    public Order() {

    }

    public Order(Integer statusId, Integer sushiId) {
        this.statusId = statusId;
        this.sushiId = sushiId;
    }
}