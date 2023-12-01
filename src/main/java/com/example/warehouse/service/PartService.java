package com.example.warehouse.service;

import com.example.warehouse.entity.Part;
import com.example.warehouse.repository.PartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PartService {

    private final PartRepository partRepository;

    /**
     * 새로운 부품을 생성하고 저장하되,
     * 동일한 이름의 부품 허용 안함
     *
     * @param part 생성하고자 하는 Part 객체. 부품의 이름은 유일해야 함
     * @return 데이터베이스에 저장된 Part 객체이며, id 포함
     * @throws IllegalStateException 동일한 제목의 Part 가 이미 존재하는 경우
     */
    @Transactional
    public Part createPart(Part part) {
        Optional<Part> foundPart = partRepository.findByName(part.getName());
        validatePartExist(foundPart);
        Part savedPart = partRepository.save(part);
        return savedPart;
    }

    private static void validatePartExist(Optional<Part> foundPart) {
        foundPart.ifPresent(t -> {
            throw new IllegalStateException("이미 존재하는 부품입니다.");
        });
    }

    /**
     * 모든 부품 조회
     *
     * @return Part 객체의 리스트. 부품 내역이 없다면 빈 리스트 반환
     */
    public List<Part> findParts() {
        return partRepository.findAll();
    }

    /**
     * ID 기준 부품 삭제
     *
     * @param partId 삭제하고자 하는 Part 의 ID
     */
    @Transactional
    public void deletePart(Long partId) {
        partRepository.deleteById(partId);
    }
}
