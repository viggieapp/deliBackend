package net.simihost.deli.exceptions;

import org.springframework.http.HttpStatus;

/**
 *
 * Created by Rashed on  23/05/2019
 *
 */
public class DuplicateUserException extends BaseWebApplicationException {

    public DuplicateUserException() {
        super(HttpStatus.CONFLICT.value(), "002", "User Already Exists", "An attempt was made to create a user that already exists.");
    }

    public DuplicateUserException(int httpStatus, String errorCode, String errorMessage, String applicationMessage) {
        super(httpStatus, errorCode, errorMessage, applicationMessage);
    }
}
