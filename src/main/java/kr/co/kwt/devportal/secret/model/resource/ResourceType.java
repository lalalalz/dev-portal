package kr.co.kwt.devportal.secret.model.resource;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResourceType {

    MYSQL(true),
    REDIS(false),
    ;

    private final boolean supportsProvisionAccount;
}
