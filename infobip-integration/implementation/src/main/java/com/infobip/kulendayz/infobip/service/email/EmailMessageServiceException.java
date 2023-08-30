package com.infobip.kulendayz.infobip.service.email;

public class EmailMessageServiceException extends RuntimeException {

    public EmailMessageServiceException(final String message) {
        super(message);
    }

    public EmailMessageServiceException(final String message, final Throwable cause) {
        super(message, cause);
    }

}
