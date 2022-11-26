package com.example.newestlinen.dto.product;

import com.example.newestlinen.storage.model.Category;
import lombok.Data;

import java.util.List;

@Data
public class CategorySelectDTO {
    private Long id;
    private String name;
    private String description;
    private List<CategorySelectDTO> categoryList;
}
