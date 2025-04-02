package kr.co.kwt.devportal.secret.configuration;

import kr.co.kwt.devportal.secret.model.resource.Resource;
import kr.co.kwt.devportal.secret.repository.resource.ResourceRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
public class ResourceProviderConfiguration {

    @Bean
    public Resource.ResourceProvider resourceProvider(ResourceRepository resourceRepository) {
        return new Resource.ResourceProvider(resourceRepository
                .findByIsPresetTrue()
                .stream()
                .collect(Collectors.toMap(Resource::getResourceType, Function.identity())));
    }
}
