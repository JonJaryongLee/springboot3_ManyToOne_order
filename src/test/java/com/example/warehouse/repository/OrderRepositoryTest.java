package com.example.warehouse.repository;

import com.example.warehouse.entity.Order;
import com.example.warehouse.entity.Part;
import com.example.warehouse.repository.OrderRepository;
import com.example.warehouse.repository.PartRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderRepositoryTest {
    @Autowired OrderRepository orderRepository;

    @Autowired PartRepository partRepository;

    @Test
    void testFindAll() {
        // given
        Part part1 = new Part();
        part1.setName("test1");
        part1.setPrice(1000L);
        Part savedPart1 = partRepository.save(part1);

        Order order1 = new Order();
        order1.setPart(savedPart1);
        order1.setOrderDate(LocalDateTime.now());
        orderRepository.save(order1);

        Part part2 = new Part();
        part2.setName("test2");
        part2.setPrice(2000L);
        Part savedPart2 = partRepository.save(part2);

        Order order2 = new Order();
        order2.setPart(savedPart2);
        order2.setOrderDate(LocalDateTime.now());
        orderRepository.save(order2);

        // when
        List<Order> orders = orderRepository.findAll();

        // then
        Assertions.assertThat(orders.size()).isEqualTo(2);
    }

    @Test
    void testDelete() {
        // given
        Part part = new Part();
        part.setName("test");
        part.setPrice(1000L);
        Part savedPart = partRepository.save(part);

        Order order = new Order();
        order.setPart(savedPart);
        order.setOrderDate(LocalDateTime.now());
        Order savedOrder = orderRepository.save(order);

        // when
        orderRepository.deleteById(savedOrder.getId());
        Order deletedOrder = orderRepository.findById(savedOrder.getId()).orElse(null);

        // then
        Assertions.assertThat(deletedOrder).isNull();
    }
}