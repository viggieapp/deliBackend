package net.simihost.deli.helper;

import org.apache.commons.validator.routines.EmailValidator;

import java.security.SecureRandom;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 *
 * Created by Rashed on  15/04/2019
 *
 */
public class UtilHelper {

    public static String generateRandomUUID() {
        return UUID.randomUUID().toString();
    }

    public static String generateRandomToken(int length) {
        SecureRandom random = new SecureRandom();
        return String.format("%05d",random.nextInt((int)Math.pow(10, length)));
    }

    public static boolean isValidEmail(String email) {
        if (email == null) return false;

        return EmailValidator.getInstance().isValid(email);
    }

    public static boolean isValidMobileNumber(String mobileNumber) {
        boolean isValid = false;

        if(mobileNumber == null) return false;

        String mobileRegex = "^249\\d{9}$";
        return mobileNumber.matches(mobileRegex);
    }

    public static Date calculateExpiryDate(int expiryDurationInMinutes) {
        Date now = new Date();
        Calendar calender = Calendar.getInstance();
        calender.setTime(now);
        calender.add(Calendar.MINUTE, expiryDurationInMinutes);
        return calender.getTime();
    }

    public static <T extends Enum<T>> T getEnumFromString(Class<T> enumClass, String value) {
        if (enumClass == null) {
            throw new IllegalArgumentException("EnumClass value can't be null.");
        }

        for (Enum<?> enumValue : enumClass.getEnumConstants()) {
            if (enumValue.toString().equalsIgnoreCase(value)) {
                return (T) enumValue;
            }
        }

        //Construct an error message that indicates all possible values for the enum.
        StringBuilder errorMessage = new StringBuilder();
        boolean bFirstTime = true;
        for (Enum<?> enumValue : enumClass.getEnumConstants()) {
            errorMessage.append(bFirstTime ? "" : ", ").append(enumValue);
            bFirstTime = false;
        }
        throw new IllegalArgumentException(value + " is invalid value. Supported values are " + errorMessage);
    }
}
