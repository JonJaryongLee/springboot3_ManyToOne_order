package com.example.warehouse.repository;

import com.example.warehouse.entity.Order;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Override
    @EntityGraph(value = "order_with_part")
    List<Order> findAll();

    @Override
    @EntityGraph(value = "order_with_part")
    Optional<Order> findById(Long id);
}
