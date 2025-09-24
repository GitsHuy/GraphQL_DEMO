package com.yourcompany.projectname.dto;

import lombok.Data;

@Data
public class ProductInput {
    private String title;
    private int quantity;
    private String desc;
    private float price;
    private Long userId;
    private Long categoryId; // Thêm categoryId
}
