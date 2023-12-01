package com.example.warehouse.service;

import com.example.warehouse.entity.Order;
import com.example.warehouse.entity.Part;
import com.example.warehouse.repository.OrderRepository;
import com.example.warehouse.repository.PartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final PartRepository partRepository;

    /**
     * 모든 주문 검색
     * @return 모든 주문의 목록 반환
     */
    public List<Order> findOrders() {
        return orderRepository.findAll();
    }

    /**
     * 새로운 주문 생성
     * @param partId 주문에 포함될 부품의 ID
     * @return 저장된 주문 반환. 부품이 존재하지 않으면 예외 발생
     */
    @Transactional
    public Order createOrder(Long partId) {
        Part part = validatePartExist(partId);

        Order order = new Order();
        order.setPart(part);
        order.setOrderDate(LocalDateTime.now());
        Order savedOrder = orderRepository.save(order);
        return savedOrder;
    }

    /**
     * 부품의 존재 검증
     * @param partId 검증할 부품의 ID
     * @return 존재하는 경우, 해당 부품 반환하고, 그렇지 않으면 예외 발생
     */
    private Part validatePartExist(Long partId) {
        return partRepository.findById(partId).orElseThrow(() -> {
            throw new IllegalArgumentException("해당 부품을 찾을 수 없습니다. id=" + partId);
        });
    }

    /**
     * 주문 삭제
     * @param orderId 삭제 주문의 ID
     * @return 삭제 주문의 ID
     */
    @Transactional
    public Long deleteOrder(Long orderId) {
        Order order = validateOrderExist(orderId);
        orderRepository.deleteById(order.getId());
        return order.getId();
    }

    /**
     * 주문의 존재 검증
     * @param orderId 검증할 주문의 ID
     * @return 존재하는 경우, 해당 주문을 반환하며, 그렇지 않으면 예외 발생
     */
    private Order validateOrderExist(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> {
            throw new IllegalArgumentException("해당 주문을 찾을 수 없습니다. id=" + orderId);
        });
    }

    /**
     * 전체 주문 메뉴 가격 합산
     * @return 전체 주문 메뉴 가격 합계 반환
     */
    public Long calculateTotalOrderPrice() {
        List<Order> orders = orderRepository.findAll();
        Long total = 0L;
        for (Order order : orders) {
            total += order.getPart().getPrice();
        }
        return total;
    }
}
