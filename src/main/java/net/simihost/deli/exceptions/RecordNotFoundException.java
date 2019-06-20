package net.simihost.deli.exceptions;

import org.springframework.http.HttpStatus;

/**
 *
 * Created by Rashed on  07/05/2019
 *
 */
public class RecordNotFoundException extends BaseWebApplicationException {

    public RecordNotFoundException() {
        super(HttpStatus.NOT_FOUND.value(), "002", "Entity Not Found", "Requested Record Not Found.");
    }

    public RecordNotFoundException(int httpStatus, String errorCode, String errorMessage, String applicationMessage) {
        super(httpStatus, errorCode, errorMessage, applicationMessage);
    }
}
