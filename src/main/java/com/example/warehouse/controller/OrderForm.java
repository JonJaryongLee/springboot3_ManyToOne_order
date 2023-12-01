package com.example.warehouse.controller;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderForm {

    @NotEmpty(message = "부품 이름은 필수입니다.")
    private String partName;
}