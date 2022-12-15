package com.example.newestlinen.validation.impl;

import com.example.newestlinen.constant.LinenAConstant;
import com.example.newestlinen.validation.Status;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class StatusValidation  implements ConstraintValidator<Status, Integer> {
    private boolean allowNull;

    @Override
    public void initialize(Status constraintAnnotation) {
        allowNull = constraintAnnotation.allowNull();
    }
    @Override
    public boolean isValid(Integer status, ConstraintValidatorContext constraintValidatorContext) {
        if(status == null && allowNull){
            return true;
        }
        if(!Objects.equals(status, LinenAConstant.STATUS_ACTIVE)
                && !Objects.equals(status, LinenAConstant.STATUS_LOCK)
                && !Objects.equals(status, LinenAConstant.STATUS_DELETE)
                && !Objects.equals(status, LinenAConstant.STATUS_PENDING)){
            return false;
        }
        return true;
    }
}
