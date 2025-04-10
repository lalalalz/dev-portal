package kr.co.kwt.devportal.secret.service.command;

import kr.co.kwt.devportal.secret.model.Environment;
import kr.co.kwt.devportal.secret.model.ResourceType;
import lombok.Value;

import java.util.List;
import java.util.Map;

@Value
public class AddResourceConfigurationsCommand {

    String service;
    Environment environment;
    List<ResourceType> resourceTypes;

    @Value
    public static class AddResourceConfigurationResult<T> {
        Map<String, Object> resourceProperties;
    }
}
