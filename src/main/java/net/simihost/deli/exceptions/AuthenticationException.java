package net.simihost.deli.exceptions;

import org.springframework.http.HttpStatus;

/**
 *
 * Created by Rashed on  23/05/2019
 *
 */
public class AuthenticationException extends BaseWebApplicationException {

    public AuthenticationException() {
        super(HttpStatus.UNAUTHORIZED.value(), "002", "UnAuthorized", "Authentication Error. The username or password are incorrect.");
    }

    public AuthenticationException(int httpStatus, String errorCode, String errorMessage, String applicationMessage) {
        super(httpStatus, errorCode, errorMessage, applicationMessage);
    }
}
