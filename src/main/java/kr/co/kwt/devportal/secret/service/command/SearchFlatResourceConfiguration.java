package kr.co.kwt.devportal.secret.service.command;

import lombok.Value;

import java.util.Map;

@Value
public class SearchFlatResourceConfiguration {

    Map<String, Object> configurations;

    public static SearchFlatResourceConfiguration from(Map<String, Object> flattenConfiguration) {
        return new SearchFlatResourceConfiguration(flattenConfiguration);
    }
}
