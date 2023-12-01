package com.example.warehouse.repository;

import com.example.warehouse.entity.Part;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
class OrderRepositoryTest {

    @Autowired PartRepository partRepository;
    @Autowired OrderRepository orderRepository;

    @Test
    void findByIdTest() {
        // given
        Part part = new Part();
        part.setName("헤드램프");
        part.setPrice(10000L);
        Part savedPart = partRepository.save(part);

        // when
        Part foundPart = partRepository.findById(savedPart.getId()).get();

        // then
        Assertions.assertThat(foundPart.getId()).isEqualTo(savedPart.getId());
        Assertions.assertThat(foundPart.getName()).isEqualTo(savedPart.getName());
        Assertions.assertThat(foundPart.getPrice()).isEqualTo(savedPart.getPrice());
    }

    @Test
    void findByIdNotFoundTest() {
        // given

        // when
        Optional<Part> foundPart = partRepository.findById(99L);

        // then
        Assertions.assertThat(foundPart).isEqualTo(Optional.empty());
    }

    @Test
    void findAllTest() {
        // given
        Part part1 = new Part();
        part1.setName("헤드램프");
        part1.setPrice(20000L);
        partRepository.save(part1);

        Part part2 = new Part();
        part2.setName("시그널램프");
        part2.setPrice(40000L);
        partRepository.save(part2);

        // when
        List<Part> parts = partRepository.findAll();

        // then
        Assertions.assertThat(parts.size()).isEqualTo(2);
    }

    @Test
    void findAllEmptyTest() {
        // given

        // when
        List<Part> parts = partRepository.findAll();

        // then
        Assertions.assertThat(parts.size()).isEqualTo(0);
    }

    @Test
    void findByNameTest() {
        // given
        Part part = new Part();
        part.setName("헤드램프");
        part.setPrice(20000L);
        partRepository.save(part);

        // when
        Part foundPart = partRepository.findByName("헤드램프").get();

        // then
        Assertions.assertThat(part.getId()).isEqualTo(foundPart.getId());
        Assertions.assertThat(part.getName()).isEqualTo(foundPart.getName());
        Assertions.assertThat(part.getPrice()).isEqualTo(foundPart.getPrice());
    }

    @Test
    void findByNameNotFoundTest() {
        // given

        // when
        Optional<Part> foundPart = partRepository.findByName("헤드램프");

        // then
        Assertions.assertThat(foundPart).isEqualTo(Optional.empty());
    }

    @Test
    void saveForUpdateTest() {
        // given
        Part part = new Part();
        part.setName("헤드램프");
        part.setPrice(10000L);
        Part savedPart = partRepository.save(part);

        // when
        savedPart.setName("헤드램프");
        Part updatedPart = partRepository.save(savedPart);
        Part foundPart = partRepository.findById(updatedPart.getId()).get();

        // then
        Assertions.assertThat(foundPart.getId()).isEqualTo(updatedPart.getId());
        Assertions.assertThat(foundPart.getName()).isEqualTo(updatedPart.getName());
        Assertions.assertThat(foundPart.getPrice()).isEqualTo(updatedPart.getPrice());
    }

    @Test
    void deleteByIdTest() {
        // given
        Part part = new Part();
        part.setName("헤드램프");
        part.setPrice(20000L);
        Part savedPart = partRepository.save(part);
        Long savedPartId = savedPart.getId();

        // when
        partRepository.deleteById(savedPartId);
        Optional<Part> foundPart = partRepository.findById(savedPartId);

        // then
        Assertions.assertThat(foundPart).isEqualTo(Optional.empty());
    }
}