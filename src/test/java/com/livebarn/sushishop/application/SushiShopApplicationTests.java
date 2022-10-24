package com.livebarn.sushishop.application;

import com.livebarn.sushishop.controller.OrderController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SushiShopApplicationTests {
    @Autowired
    OrderController orderController;

    @Test
    void contextLoads() {
        assertThat(orderController).isNotNull();
    }
}