package com.example.warehouse.controller;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PartForm {

    @NotEmpty(message = "부품 이름은 필수입니다.")
    private String name;

    @NotNull(message = "부품 가격은 필수입니다.")
    private Long price;
}