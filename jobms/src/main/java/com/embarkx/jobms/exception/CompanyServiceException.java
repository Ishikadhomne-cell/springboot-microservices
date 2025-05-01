package com.embarkx.jobms.exception;

public class CompanyServiceException extends RuntimeException {
    public CompanyServiceException(String message) {
        super(message);
    }

    public CompanyServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
