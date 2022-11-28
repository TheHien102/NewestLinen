package com.example.newestlinen.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
public class ApiMessageDto<T> {
    private Boolean result = true;
    private String code = null;
    private T data = null;
    private String message = null;
    private HttpStatus status;

    public ApiMessageDto(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

    public ApiMessageDto(String message,HttpStatus status,Boolean result){
        this.message = message;
        this.status = status;
        this.result=result;
    }
}
