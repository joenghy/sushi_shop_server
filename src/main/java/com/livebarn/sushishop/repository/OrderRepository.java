package com.livebarn.sushishop.repository;

import com.livebarn.sushishop.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface OrderRepository extends JpaRepository<Order, Integer> {
    @Query(value = "select id from sushi_order where status_id = 2", nativeQuery = true)
    List<Integer> getCurrentOrders();

    @Query(value = "select id from sushi_order o where o.status_id = 2 and now() > dateadd(ss, (select time_to_make from sushi s where s.id = o.sushi_id), o.created_at)", nativeQuery = true)
    List<Integer> getFinishedOrders();

    @Query(value  = "select id from sushi_order o where o.status_id = 1 order by o.created_at limit ?1", nativeQuery = true)
    List<Integer> getPendingOrders(Integer num);

    @Modifying
    @Query(value = "update sushi_order o set o.status_id = ?2 where o.id = ?1", nativeQuery = true)
    void setStatusById(Integer id, Integer status_id);
}
