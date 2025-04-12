package kr.co.kwt.devportal.secret.v2.model.register;

public class MySqlProvisioningAccountRegisterException extends RuntimeException {
    public MySqlProvisioningAccountRegisterException() {
    }

    public MySqlProvisioningAccountRegisterException(String message) {
        super(message);
    }

    public MySqlProvisioningAccountRegisterException(String message, Throwable cause) {
        super(message, cause);
    }

    public MySqlProvisioningAccountRegisterException(Throwable cause) {
        super(cause);
    }

    public MySqlProvisioningAccountRegisterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
