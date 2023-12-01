package com.example.warehouse.controller;

import com.example.warehouse.entity.Order;
import com.example.warehouse.entity.Part;
import com.example.warehouse.repository.PartRepository;
import com.example.warehouse.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final PartRepository partRepository;

    /**
     * "GET /orders" 처리. 부품 페이지 반환.
     *
     * @param model 스프링 Model 객체. 페이지에 데이터 전달.
     * @return "orderList" 반환할 HTML 파일 이름
     */
    @GetMapping("/orders")
    public String list(Model model) {
        List<Order> orders = orderService.findOrders();
        Long totalOrderPrice = orderService.calculateTotalOrderPrice();
        model.addAttribute("orders", orders);
        model.addAttribute("totalOrderPrice", totalOrderPrice);
        return "orders/orderList";
    }

    /**
     * "POST /parts/delete/{orderId}" 처리. id 에 해당하는 값 삭제
     *
     * @param orderId 삭제할 주문의 ID
     * @return "redirect:/orders" 삭제 후 "/orders" 로 리다이렉트
     */
    @PostMapping("/orders/delete/{orderId}")
    public String delete(@PathVariable(name="orderId") Long orderId) {
        orderService.deleteOrder(orderId);
        return "redirect:/orders";
    }

    /**
     * "GET /orders/new" 처리. 입력 폼 보여줌
     *
     * @return "parts/createOrderForm" Order 생성 폼
     */
    @GetMapping("/orders/new")
    public String createForm(Model model) {
        model.addAttribute("orderForm", new OrderForm());
        return "orders/createOrderForm";
    }

    /**
     * 주문 생성
     *
     * @param orderForm 주문 정보를 담고 있는 OrderForm 객체
     * @param result 유효성 검사 결과를 담고 있는 BindingResult 객체
     * @return 주문 생성 성공 시 주문 목록 페이지로 리다이렉트. 유효성 검사에서 오류가 발생하면 주문 생성 폼 페이지로 이동
     * @throws NoSuchElementException 주문하려는 부품이 존재하지 않을 경우 예외 발생
     */
    @PostMapping("/orders/new")
    public String create(@Valid OrderForm orderForm, BindingResult result) {
        if (result.hasErrors()) {
            return "orders/createOrderForm";
        }
        String partName = orderForm.getPartName();
        Part part = partRepository.findByName(partName)
                .orElseThrow(() -> new NoSuchElementException(partName + ": 해당 부품이 존재하지 않습니다."));
        Long id = part.getId();
        orderService.createOrder(id);
        return "redirect:/orders";
    }
}
