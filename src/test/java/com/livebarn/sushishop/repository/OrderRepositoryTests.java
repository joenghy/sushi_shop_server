package com.livebarn.sushishop.repository;

import com.livebarn.sushishop.model.Order;
import com.livebarn.sushishop.model.Status;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class OrderRepositoryTests {
    @Autowired
    private OrderRepository underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void shouldSetStatusIdForOrderInDatabase() {
        // given
        Order order = new Order(Status.CREATED.getStatusId(), 1);
        underTest.save(order);

        // when
        underTest.setStatusById(order.getId(), Status.PAUSED.getStatusId());

        // then
        Integer statusId = underTest.getStatusById(order.getId());
        assertThat(statusId).isEqualTo(Status.PAUSED.getStatusId());
    }

    @Test
    void shouldReturnNullForOrderThatDoesNotExistInDatabase() {
        // given

        // when
        underTest.setStatusById(1, Status.PAUSED.getStatusId());

        // then
        Integer statusId = underTest.getStatusById(1);
        assertThat(statusId).isNull();
    }

    @Test
    void shouldGetTheSameStatusIdWithTheStatusIdWeInitializeTheOrder() {
        // given
        Order order = new Order(Status.CREATED.getStatusId(), 1);
        underTest.save(order);

        // when

        // then
        Integer statusId = underTest.getStatusById(order.getId());
        assertThat(statusId).isEqualTo(Status.CREATED.getStatusId());
    }
}