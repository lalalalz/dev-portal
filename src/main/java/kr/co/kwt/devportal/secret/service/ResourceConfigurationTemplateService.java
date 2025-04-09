package kr.co.kwt.devportal.secret.service;

import kr.co.kwt.devportal.secret.model.ResourceType;
import kr.co.kwt.devportal.secret.model.property.DataSourceProperties;
import kr.co.kwt.devportal.secret.model.property.RedisProperties;
import kr.co.kwt.devportal.secret.model.template.DataSourceResourceConfigurationTemplate;
import kr.co.kwt.devportal.secret.model.template.RedisResourceConfigurationTemplate;
import kr.co.kwt.devportal.secret.model.template.ResourceConfigurationTemplate;
import kr.co.kwt.devportal.secret.repository.ResourceConfigurationTemplateRepository;
import kr.co.kwt.devportal.secret.service.command.AddResourceConfigurationTemplateCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

@Service
@RequiredArgsConstructor
public class ResourceConfigurationTemplateService {

    private final ResourceConfigurationTemplateRepository resourceConfigurationTemplateRepository;

    public List<ResourceConfigurationTemplate<?>> getResourceConfigurationTemplates() {
        return resourceConfigurationTemplateRepository.findAll();
    }

    public Map<ResourceType, ResourceConfigurationTemplate<?>> getResourceTypeResourceConfigurationTemplateMap() {
        return getResourceConfigurationTemplates()
                .stream()
                .collect(toMap(ResourceConfigurationTemplate::getResourceType, Function.identity()));
    }

    public String addResourcePropertyTemplate(AddResourceConfigurationTemplateCommand<?> command) {
        ResourceConfigurationTemplate<?> resourceConfigurationTemplate;

        switch (command.getResourceType()) {
            case MYSQL -> resourceConfigurationTemplate = new DataSourceResourceConfigurationTemplate(
                    (DataSourceProperties) command.getResourceProperties());
            case REDIS -> resourceConfigurationTemplate = new RedisResourceConfigurationTemplate(
                    (RedisProperties) command.getResourceProperties());
            default -> throw new UnsupportedResourceTypeException();
        }

        return resourceConfigurationTemplateRepository
                .save(resourceConfigurationTemplate)
                .getId();
    }
}
