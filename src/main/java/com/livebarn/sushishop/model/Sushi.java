package com.livebarn.sushishop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
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
    @JsonIgnore
    private Integer id;
    // The field in request body in sushi_name, not name
    @JsonProperty("sushi_name")
    @ApiModelProperty(example="California Roll")
    private String name;
    @JsonIgnore
    private Integer time_to_make;
}