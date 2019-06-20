package net.simihost.deli.validation;

import org.apache.commons.beanutils.BeanUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 * Created by Rashed on  23/05/2019
 *
 */
public class EitherNotNullValidator implements ConstraintValidator<EitherNotNull, Object> {

    private String firstFieldName;
    private String secondFieldName;
    private String errorMessage;

    @Override
    public void initialize(EitherNotNull constraintAnnotation) {
        firstFieldName = constraintAnnotation.first();
        secondFieldName = constraintAnnotation.second();
        errorMessage = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext cvc) {
        boolean toReturn = false;

        try {
            final Object firstObj = BeanUtils.getProperty(value, firstFieldName);
            final Object secondObj = BeanUtils.getProperty(value, secondFieldName);
            toReturn = !(firstObj == null && secondObj == null);
        } catch (final Exception e) {}

        // If the validation failed
        if (!toReturn) {
            cvc.disableDefaultConstraintViolation();
            // In the initialiaze method you get the errorMessage:
            // constraintAnnotation.message();
            cvc.buildConstraintViolationWithTemplate(errorMessage)
                    .addNode(secondFieldName).addConstraintViolation();
        }

        return toReturn;
    }
}
