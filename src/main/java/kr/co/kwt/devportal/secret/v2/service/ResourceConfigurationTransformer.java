package kr.co.kwt.devportal.secret.v2.service;

import kr.co.kwt.devportal.secret.v2.model.ResourceConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static kr.co.kwt.devportal.secret.v2.utility.Flattener.flattenMap;

@Component
@RequiredArgsConstructor
public class ResourceConfigurationTransformer {

    public Map<String, Object> transform(List<ResourceConfiguration> resourceConfigurations) {
        return resourceConfigurations
                .stream()
                .map(resourceConfiguration -> Stream
                        .concat(flattenMap(resourceConfiguration
                                        .getResourceConfigurationTemplate()
                                        .getProperties())
                                        .entrySet()
                                        .stream(),
                                flattenMap(resourceConfiguration
                                        .getProvisioningAccount())
                                        .entrySet()
                                        .stream())
                        .filter(entry -> entry.getValue() != null)
                        .collect(Collectors.toMap(
                                entry -> (resourceConfiguration
                                        .getResourceConfigurationTemplate()
                                        .getResourceType()
                                        .name() + "_" + entry.getKey()).toUpperCase(),
                                Map.Entry::getValue)))
                .collect(HashMap::new, Map::putAll, Map::putAll);
    }
}
