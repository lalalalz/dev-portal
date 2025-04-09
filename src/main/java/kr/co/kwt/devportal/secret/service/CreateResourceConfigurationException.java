package kr.co.kwt.devportal.secret.service;

public class CreateResourceConfigurationException extends RuntimeException {
    public CreateResourceConfigurationException() {
    }

    public CreateResourceConfigurationException(String message) {
        super(message);
    }

    public CreateResourceConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

    public CreateResourceConfigurationException(Throwable cause) {
        super(cause);
    }

    public CreateResourceConfigurationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
