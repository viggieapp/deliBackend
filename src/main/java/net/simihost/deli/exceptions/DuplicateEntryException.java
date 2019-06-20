package net.simihost.deli.exceptions;

import org.springframework.http.HttpStatus;

/**
 *
 * Created by Rashed on  07/05/2019
 *
 */
public class DuplicateEntryException extends BaseWebApplicationException {

    public DuplicateEntryException() {
        super(HttpStatus.CONFLICT.value(), "002", "Duplicate Entry", "Unique Constraint Violation.");
    }

    public DuplicateEntryException(int httpStatus, String errorCode, String errorMessage, String applicationMessage) {
        super(httpStatus, errorCode, errorMessage, applicationMessage);
    }
}
