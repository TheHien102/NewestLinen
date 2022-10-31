package com.example.newestlinen.validation;

import com.example.newestlinen.validation.impl.StatusValidation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = StatusValidation.class)
@Documented
public @interface Status {
    boolean allowNull() default true;
    String message() default "Status invalid.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
