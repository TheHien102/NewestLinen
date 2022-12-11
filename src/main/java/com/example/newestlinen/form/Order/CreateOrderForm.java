package com.example.newestlinen.form.Order;

import com.example.newestlinen.storage.model.OrderModel.OrderDetail;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.*;
import java.util.List;

@Data
@Schema
public class CreateOrderForm {
    @Schema(name = "cartItemIdsList")
    @Size(min = 1,message = "username should between {min} and {max} character")
    private List<Long> cartItemIdsList;

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
}
