package com.example.newestlinen.exception;

import com.cloudinary.Api;
import com.example.newestlinen.dto.ApiMessageDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@RestControllerAdvice
public class FormErrorException {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiMessageDto<String> handleValidForm(MethodArgumentNotValidException methodArgumentNotValidException) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();

        apiMessageDto.setResult(false);
        apiMessageDto.setMessage(methodArgumentNotValidException.getBindingResult().getFieldErrors().get(0).getDefaultMessage());
        apiMessageDto.setStatus(HttpStatus.BAD_REQUEST);
        return apiMessageDto;
    }
}
