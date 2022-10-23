package com.livebarn.sushishop.model;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    private Integer id;
    private Integer status_id;
    private Integer sushi_id;
    @CreationTimestamp
    private Timestamp createdAt;
}