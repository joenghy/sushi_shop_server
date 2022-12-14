package com.livebarn.sushishop.repository;

import com.livebarn.sushishop.model.Sushi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SushiRepository extends JpaRepository<Sushi, Integer> {
    @Query(value = "select id from sushi s where s.name = ?1", nativeQuery = true)
    Integer findIdByName(String name);

    @Query(value = "select time_to_make from sushi s where s.name = ?1", nativeQuery = true)
    Integer findTTMByName(String name);
}