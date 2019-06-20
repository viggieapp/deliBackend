package net.simihost.deli.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 *
 * Created by Rashed on  02/04/2019
 *
 */
@Target({ METHOD, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = {MobileNoValidator.class})
@Documented
public @interface ValidMobileNo {

	String message() default "Please provide a valid mobile No (starts with 249).";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
