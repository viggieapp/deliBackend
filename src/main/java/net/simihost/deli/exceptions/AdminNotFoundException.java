package net.simihost.deli.exceptions;

import org.springframework.http.HttpStatus;

/**
 *
 * Created by Rashed on  28/04/2019
 *
 */
public class AdminNotFoundException extends BaseWebApplicationException {

    public AdminNotFoundException() {
        super(HttpStatus.NOT_FOUND.value(), "002", "Admin Not Found", "No Admin could be found for that Id.");
    }

    public AdminNotFoundException(int httpStatus, String errorCode, String errorMessage, String applicationMessage) {
        super(httpStatus, errorCode, errorMessage, applicationMessage);
    }
}
