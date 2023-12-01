package com.example.warehouse.service;

import com.example.warehouse.entity.Part;
import com.example.warehouse.repository.PartRepository;
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
class PartServiceTest {

    @Autowired PartService partService;
    @Autowired PartRepository partRepository;

    @Test
    void createPartTest() {
        // given
        Part part = new Part();
        part.setName("헤드램프");
        part.setPrice(20000L);
        Part savedPart = partService.createPart(part);
        Long savedId = savedPart.getId();

        // when
        Part foundPart = partRepository.findById(savedId).get();

        // then
        Assertions.assertThat(foundPart.getId()).isEqualTo(savedId);
        Assertions.assertThat(foundPart.getName()).isEqualTo(savedPart.getName());
        Assertions.assertThat(foundPart.getPrice()).isEqualTo(savedPart.getPrice());
    }

    @Test
    void createPartDuplicateTest() {
        // given
        Part part = new Part();
        part.setName("헤드램프");
        part.setPrice(20000L);
        partService.createPart(part);

        // when
        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> partService.createPart(part));

        // then
        Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 부품입니다.");
    }

    @Test
    void findPartsTest() {
        // given
        Part part1 = new Part();
        part1.setName("헤드램프");
        part1.setPrice(20000L);
        partService.createPart(part1);
        Part part2 = new Part();
        part2.setName("시그널램프");
        part2.setPrice(30000L);
        partService.createPart(part2);

        // when
        List<Part> foundParts = partService.findParts();

        // then
        Assertions.assertThat(foundParts.size()).isEqualTo(2);
    }

    @Test
    void findPartsEmptyTest() {
        // given

        // when
        List<Part> foundParts = partService.findParts();

        // then
        Assertions.assertThat(foundParts.size()).isEqualTo(0);
    }

    @Test
    void deletePartTest() {
        // given
        Part part = new Part();
        part.setName("헤드램프");
        part.setPrice(20000L);
        Part savedPart = partService.createPart(part);
        Long savedPartId = savedPart.getId();

        // when
        partService.deletePart(savedPartId);
        Optional<Part> foundPart = partRepository.findById(savedPartId);

        // then
        Assertions.assertThat(foundPart).isEqualTo(Optional.empty());
    }
}