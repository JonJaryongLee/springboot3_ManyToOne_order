package com.example.warehouse.controller;

import com.example.warehouse.entity.Part;
import com.example.warehouse.service.PartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PartController {

    private final PartService partService;

    /**
     * "GET /parts" 처리. 부품 페이지 반환.
     *
     * @param model 스프링 Model 객체. 페이지에 데이터 전달.
     * @return "partList" 반환할 HTML 파일 이름
     */
    @GetMapping("/parts")
    public String list(Model model) {
        List<Part> parts = partService.findParts();
        model.addAttribute("parts", parts);
        return "parts/partList";
    }

    /**
     * "POST /parts/delete/{partId}" 처리. id 에 해당하는 값 삭제
     *
     * @param partId 삭제할 부품의 ID
     * @return "redirect:/parts" 삭제 후 "/parts" 로 리다이렉트
     */
    @PostMapping("/parts/delete/{partId}")
    public String delete(@PathVariable(name="partId") Long partId) {
        partService.deletePart(partId);
        return "redirect:/parts";
    }

    /**
     * "GET /parts/new" 처리. 입력 폼 보여줌
     *
     * @return "parts/createPartForm" Part 생성 폼
     */
    @GetMapping("/parts/new")
    public String createForm(Model model) {
        model.addAttribute("partForm", new PartForm());
        return "parts/createPartForm";
    }

    /**
     * "POST /parts/new" 처리. Part 생성
     *
     * @param partForm {@link Valid} 어노테이션을 사용하여 유효성 검사가 수행된 partForm
     * @param result {@link PartForm} 유효성 검사 결과
     * @return 만약 에러가 있으면 에러 메시지를 표시, 없으면 "/" 으로 리다이렉트
     */
    @PostMapping("/parts/new")
    public String create(@Valid PartForm partForm, BindingResult result) {
        if (result.hasErrors()) {
            return "parts/createPartForm";
        }
        Part part = new Part();
        part.setName(partForm.getName());
        part.setPrice(partForm.getPrice());
        partService.createPart(part);
        return "redirect:/parts";
    }
}
