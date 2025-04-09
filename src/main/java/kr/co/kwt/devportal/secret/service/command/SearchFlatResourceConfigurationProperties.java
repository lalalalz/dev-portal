package kr.co.kwt.devportal.secret.service.command;

import lombok.Value;

import java.util.Map;

@Value
public class SearchFlatResourceConfigurationProperties {

    Map<String, Object> properties;

    public static SearchFlatResourceConfigurationProperties from(Map<String, Object> flattenConfiguration) {
        return new SearchFlatResourceConfigurationProperties(flattenConfiguration);
    }
}
