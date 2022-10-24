package com.livebarn.sushishop.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import javax.persistence.*;

@Data
@Entity
@Table
public class Sushi {
    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    private Integer id;
    // The field in request body in sushi_name, not name
    @JsonProperty("sushi_name")
    private String name;
    private Integer time_to_make;
}