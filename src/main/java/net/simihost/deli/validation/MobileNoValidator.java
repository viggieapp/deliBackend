package net.simihost.deli.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 * Created by Rashed on  02/04/2019
 *
 */
public class MobileNoValidator implements ConstraintValidator<ValidMobileNo, String> {

	@Override
	public void initialize(ValidMobileNo arg0) {}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext cvc) {
		if(value == null)
			return true;

		final String regex = "^249\\d{9}$";

		return value.matches(regex);
	}

}
