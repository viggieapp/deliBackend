package net.simihost.deli.exceptions;

import org.springframework.http.HttpStatus;

/**
 *
 * Created by Rashed on  24/04/2019
 *
 */
public class UserNotFoundException extends BaseWebApplicationException {

    public UserNotFoundException() {
        super(HttpStatus.NOT_FOUND.value(), "002", "User Not Found", "No User could be found for that Id.");
    }

    public UserNotFoundException(int httpStatus, String errorCode, String errorMessage, String applicationMessage) {
        super(httpStatus, errorCode, errorMessage, applicationMessage);
    }
}
