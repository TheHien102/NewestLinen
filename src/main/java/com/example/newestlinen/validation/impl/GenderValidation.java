package com.example.newestlinen.validation.impl;

import com.example.newestlinen.constant.LinenAConstant;
import com.example.newestlinen.validation.Gender;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class GenderValidation implements ConstraintValidator<Gender, Integer> {
    private boolean allowNull;

    @Override
    public void initialize(Gender constraintAnnotation) {
        allowNull = constraintAnnotation.allowNull();
    }

    @Override
    public boolean isValid(Integer gender, ConstraintValidatorContext constraintValidatorContext) {
        if(gender == null && allowNull){
            return true;
        }
        if(!Objects.equals(gender, LinenAConstant.GENDER_FEMALE)
                && !Objects.equals(gender, LinenAConstant.GENDER_MALE)
                && !Objects.equals(gender, LinenAConstant.GENDER_OTHER)){
            return false;
        }
        return true;
    }
}
