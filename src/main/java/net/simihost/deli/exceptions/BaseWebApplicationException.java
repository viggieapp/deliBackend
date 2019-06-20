package net.simihost.deli.exceptions;

/**
 *
 * Created by Rashed on  26/03/2019
 *
 */
public abstract class BaseWebApplicationException extends RuntimeException {

    private final int status;
    private final String errorCode;
    private final String errorMessage;
    private final String applicationMessage;

    public BaseWebApplicationException(int httpStatus, String errorCode, String errorMessage, String applicationMessage) {
        super(errorMessage);
        this.status = httpStatus;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.applicationMessage = applicationMessage;
    }

    public BaseWebApplicationException(int httpStatus, int errorCode, String errorMessage, String applicationMessage) {
        super(errorMessage);
        this.status = httpStatus;
        this.errorMessage = errorMessage;
        this.errorCode = String.valueOf(errorCode);
        this.applicationMessage = applicationMessage;
    }

    public BaseWebApplicationException(int httpStatus, int errorCode, String errorMessage, String applicationMessage, Throwable cause) {
        super(errorMessage,cause);
        this.status = httpStatus;
        this.errorMessage = errorMessage;
        this.errorCode = String.valueOf(errorCode);
        this.applicationMessage = applicationMessage;
    }

    public ErrorResponse getErrorResponse() {
        ErrorResponse response = new ErrorResponse();
        response.setResponseCode(errorCode);
        response.setResponseMessage(errorMessage);
        response.setApplicationMessage(applicationMessage);
        return response;
    }

    public int getStatus() {
        return status;
    }


}
