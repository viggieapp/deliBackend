package net.simihost.deli.exceptions;

import org.springframework.http.HttpStatus;

/**
 *
 * Created by Rashed on  23/05/2019
 *
 */
public class PasswordException extends BaseWebApplicationException {

    public PasswordException() {
        super(HttpStatus.BAD_REQUEST.value(), "002", "Password Mismatch", "Password Mismatch Error.");
    }

    public PasswordException(int httpStatus, String errorCode, String errorMessage, String applicationMessage) {
        super(httpStatus, errorCode, errorMessage, applicationMessage);
    }
}
