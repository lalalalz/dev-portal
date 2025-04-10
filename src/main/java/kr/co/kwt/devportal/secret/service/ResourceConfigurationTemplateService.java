package kr.co.kwt.devportal.secret.service;

import kr.co.kwt.devportal.secret.model.Environment;
import kr.co.kwt.devportal.secret.model.ResourceType;
import kr.co.kwt.devportal.secret.model.property.DataSourceProperties;
import kr.co.kwt.devportal.secret.model.property.KafkaProperties;
import kr.co.kwt.devportal.secret.model.property.MongoProperties;
import kr.co.kwt.devportal.secret.model.property.RedisProperties;
import kr.co.kwt.devportal.secret.model.template.*;
import kr.co.kwt.devportal.secret.repository.ResourceConfigurationTemplateRepository;
import kr.co.kwt.devportal.secret.service.command.AddResourceConfigurationTemplateCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

@Service
@RequiredArgsConstructor
public class ResourceConfigurationTemplateService {

    private final ResourceConfigurationTemplateRepository resourceConfigurationTemplateRepository;

    public List<ResourceConfigurationTemplate<?>> getResourceConfigurationTemplates() {
        return resourceConfigurationTemplateRepository.findAll();
    }

    public Map<Environment, Map<ResourceType, ResourceConfigurationTemplate<?>>> getResourceTypeResourceConfigurationTemplateMap() {
        return getResourceConfigurationTemplates()
                .stream()
                .collect(Collectors.groupingBy(ResourceConfigurationTemplate::getEnvironment,
                        toMap(ResourceConfigurationTemplate::getResourceType,
                                Function.identity(),
                                (existing, replacement) -> existing)));
    }

    public String addResourcePropertyTemplate(AddResourceConfigurationTemplateCommand<?> command) {
        ResourceConfigurationTemplate<?> resourceConfigurationTemplate;

        switch (command.getResourceType()) {
            case MYSQL -> resourceConfigurationTemplate = new DataSourceResourceConfigurationTemplate(
                    command.getEnvironment(),
                    (DataSourceProperties) command.getResourceProperties());
            case REDIS -> resourceConfigurationTemplate = new RedisResourceConfigurationTemplate(
                    command.getEnvironment(),
                    (RedisProperties) command.getResourceProperties());
            case MONGO -> resourceConfigurationTemplate = new MongoResourceConfigurationTemplate(
                    command.getEnvironment(),
                    (MongoProperties) command.getResourceProperties());
            case KAFKA -> resourceConfigurationTemplate = new KafkaResourceConfigurationTemplate(
                    command.getEnvironment(),
                    (KafkaProperties) command.getResourceProperties());
            default -> throw new UnsupportedResourceTypeException();
        }

        return resourceConfigurationTemplateRepository
                .save(resourceConfigurationTemplate)
                .getId();
    }
}
