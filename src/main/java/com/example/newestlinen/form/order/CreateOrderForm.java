package com.example.newestlinen.form.order;

import com.example.newestlinen.form.cart.AddToCartForm;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.*;
import java.util.List;

@Data
@Schema
public class CreateOrderForm {
    @Schema(name = "cartItemIdsList")
    private List<Long> cartItemIdsList;

    @Schema(name = "cartItemsList")
    private List<AddToCartForm> cartItemsList;

    @Schema(name = "address")
    @NotNull(message = "address should not be null")
    @NotBlank(message = "address should not be empty")
    @NotEmpty(message = "address should not be empty")
    private String address;

    @Schema(name = "note")
    private String note;

    @Schema(name = "phoneNumber")
    @NotNull(message = "phoneNumber should not be null")
    @NotBlank(message = "phoneNumber should not be empty")
    @NotEmpty(message = "phoneNumber should not be empty")
    private String phoneNumber;

    @Schema(name = "username")
    @NotNull(message = "username should not be null")
    @NotBlank(message = "username should not be empty")
    @NotEmpty(message = "username should not be empty")
    private String username;

    @Schema(name = "paymentType")
    @NotNull(message = "paymentType should not be null")
    private Integer paymentType;

    @Schema(name = "shippingFee")
    @NotNull(message = "shippingFee should not be null")
    private Long shippingFee;

    private Integer status = 0;
}
