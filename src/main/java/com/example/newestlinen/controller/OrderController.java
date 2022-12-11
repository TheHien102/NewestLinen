package com.example.newestlinen.controller;

import com.example.newestlinen.dto.ApiMessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/order")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
@RequiredArgsConstructor
public class OrderController extends ABasicController{
    @PostMapping("/create")
    public ApiMessageDto<String> createOrder(){
        return new ApiMessageDto<>("created order", HttpStatus.OK);
    }
}
