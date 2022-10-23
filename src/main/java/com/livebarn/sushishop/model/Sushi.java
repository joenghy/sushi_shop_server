package com.livebarn.sushishop.model;

import lombok.Data;

import javax.persistence.*;

//@Data
//@Entity
@Table
public class Sushi {
    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    private Integer id;
    private String name;
    private Integer time_to_make;
}
