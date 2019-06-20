package net.simihost.deli.exceptions;

import org.springframework.http.HttpStatus;

/**
 *
 * Created by Rashed on  26/03/2019
 *
 */
public class ValidationException extends BaseWebApplicationException{

    public ValidationException(int httpStatus, int errorCode, String errorMessage, String applicationMessage, Throwable cause) {
        super(httpStatus, errorCode, errorMessage, applicationMessage, cause);
    }

    public ValidationException(int errorCode, String errorMessage, Throwable cause) {
        super(HttpStatus.BAD_REQUEST.value(), errorCode, errorMessage, null, cause);
    }

    public ValidationException(int errorCode, String errorMessage) {
        super(HttpStatus.BAD_REQUEST.value(), errorCode, errorMessage, null, null);
    }
}
