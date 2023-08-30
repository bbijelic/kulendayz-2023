package com.infobip.kulendayz.infobip.service.cdp;

public class CdpServiceException extends RuntimeException {

    public CdpServiceException(final String message) {
        super(message);
    }

    public CdpServiceException(final String message, final Throwable cause) {
        super(message, cause);
    }

}
