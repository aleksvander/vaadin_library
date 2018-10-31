package com.haulmont.solution.backend.validation.validator;

import com.haulmont.solution.backend.validation.constraint.PublisherConstraint;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class PublisherValidator implements ConstraintValidator<PublisherConstraint, String>
{
    private static final String PUBLISHER_INCORRECT_VALUE_ERROR = "The genre should be one of %s";

    @Override
    public void initialize(PublisherConstraint validateBookPublisher) {

    }

    @Override
    public boolean isValid(String publisher, ConstraintValidatorContext constraintValidatorContext) {
        boolean result = true;

        HibernateConstraintValidatorContext hibernateContext = constraintValidatorContext.unwrap(
                HibernateConstraintValidatorContext.class
        );

        if (publisher == null || !PublisherList.isContain(publisher)) {

            final String message = String.format(
                    PUBLISHER_INCORRECT_VALUE_ERROR, Arrays.toString(PublisherList.values())
            );

            hibernateContext.disableDefaultConstraintViolation();
            hibernateContext.buildConstraintViolationWithTemplate(message)
                    .addConstraintViolation();

            result = false;
        }

        return result;
    }

    private enum PublisherList {
        MOSCOW("Москва"),
        PITER("Питер"),
        OREILLY("O’Reilly");

        private final String value;

        PublisherList(String value)
        {
            this.value = value;
        }

        public String getValue()
        {
            return value;
        }

        public static boolean isContain(String s)
        {
            for (PublisherList publisherList : values())
            {
                if (publisherList.value.equals(s))
                    return true;
            }
            return false;
        }

        public String toString()
        {
            return this.getValue();
        }
    }

}
