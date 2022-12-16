package com.example.newestlinen.dto.order;

import lombok.Data;

import java.util.Date;

@Data
public class OrderDTO {
    private Long id;
    private Long totalPrice;
    private Long shippingFee;
    private String address;
    private String phoneNumber;
    private String note;
    private Integer paymentType;
    private Date createdDate;
    private Date modifiedDate;
    private Integer status;
}
