package com.example.newestlinen.dto.order;

import com.example.newestlinen.dto.product.VariantDTO;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class OrderDetailDTO {
    private Long productId;

    private List<VariantDTO> variants;

    private Integer quantity;

    private Integer price;

    private Integer discount;

    private String name;

    private String mainImg;

    private Date createdDate;

    private Date modifiedDate;
}
