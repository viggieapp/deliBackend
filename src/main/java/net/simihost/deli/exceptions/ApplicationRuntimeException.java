package net.simihost.deli.exceptions;

import org.springframework.http.HttpStatus;

/**
 *
 * Created by Rashed on  26/03/2019
 *
 */
public class ApplicationRuntimeException extends BaseWebApplicationException {

    public ApplicationRuntimeException(int httpStatus, String errorCode, String errorMessage, String applicationMessage) {
        super(httpStatus, errorCode, errorMessage, applicationMessage);
    }

    public ApplicationRuntimeException(String applicationMessage) {
        super(HttpStatus.INTERNAL_SERVER_ERROR.value(), "001", "Internal System Error", applicationMessage);
    }
}