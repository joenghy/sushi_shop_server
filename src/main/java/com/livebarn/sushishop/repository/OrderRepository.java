package com.livebarn.sushishop.repository;

import com.livebarn.sushishop.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import javax.transaction.Transactional;

@Transactional
public interface OrderRepository extends JpaRepository<Order, Integer> {
    @Modifying
    @Query(value = "update sushi_order o set o.status_id = ?2 where o.id = ?1", nativeQuery = true)
    void setStatusById(Integer id, Integer status_id);

    // for testing
    @Query(value = "select status_id from sushi_order o where o.id = ?1", nativeQuery = true)
    Integer getStatusById(Integer id);
}