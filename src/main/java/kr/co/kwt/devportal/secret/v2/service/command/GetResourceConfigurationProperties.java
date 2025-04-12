package kr.co.kwt.devportal.secret.v2.service.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@Getter
@AllArgsConstructor
public class GetResourceConfigurationProperties {

    private Map<String, Object> properties;
}
