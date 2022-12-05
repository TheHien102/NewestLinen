package com.example.newestlinen.form.cart;

import com.example.newestlinen.dto.product.VariantDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Schema
public class AddToCartForm {
    @Schema(name = "productId")
    @Min(value = 1,message = "productId shout not be null")
    private Long productId;

    @Schema(name = "variants")
    @Size(min = 1,message = "size should not be null")
    private List<VariantDTO> variants;

    @Schema(name = "quantity")
    @Min(value = 1,message = "quantity shout not be null")
    private int quantity;

    @Schema(name = "totalPrice")
    @Min(value = 1,message = "totalPrice shout not be null")
    private int totalPrice;
}
