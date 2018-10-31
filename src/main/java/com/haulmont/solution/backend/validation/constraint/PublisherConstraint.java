package com.haulmont.solution.backend.validation.constraint;

import com.haulmont.solution.backend.validation.validator.PublisherValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PublisherValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PublisherConstraint {

    String message() default "{com.haulmont.solution.backend.validation.constraint." +
            "PublisherConstraint.message}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
