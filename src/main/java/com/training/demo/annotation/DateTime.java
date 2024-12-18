package com.training.demo.annotation;

import com.training.demo.annotation.validators.DateTimeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DateTimeValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface DateTime {

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String[] subset() default {};

    String pattern();

    String message() default "時間格式不正確";
}
