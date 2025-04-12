package kr.co.kwt.devportal.secret.v2.service;

import kr.co.kwt.devportal.secret.v2.model.Environment;
import kr.co.kwt.devportal.secret.v2.model.ResourceConfiguration;
import kr.co.kwt.devportal.secret.v2.model.ResourceType;
import kr.co.kwt.devportal.secret.v2.model.register.ProvisioningAccount;
import kr.co.kwt.devportal.secret.v2.model.register.ProvisioningAccountRegister;
import kr.co.kwt.devportal.secret.v2.model.template.ResourceConfigurationTemplate;
import kr.co.kwt.devportal.secret.v2.repository.ResourceConfigurationRepository;
import kr.co.kwt.devportal.secret.v2.service.command.AddResourceConfigurationCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResourceConfigurationService {

    private final ResourceConfigurationRepository resourceConfigurationRepository;
    private final List<ProvisioningAccountRegister> provisioningAccountRegisters;
    private final ResourceConfigurationTemplateService resourceConfigurationTemplateService;

    public List<ResourceConfiguration> getResourceConfigurations(String service, Environment environment) {
        return resourceConfigurationRepository
                .findAllByServiceAndEnvironment(service, environment);
    }

    public List<ResourceConfiguration> addResourceConfiguration(AddResourceConfigurationCommand command) {
        Map<ResourceType, ResourceConfigurationTemplate<?>> resourceTypeTemplateMap =
                resourceConfigurationTemplateService
                        .searchResourceConfigurationTemplates()
                        .stream()
                        .filter(template -> template.getEnvironment().equals(command.getEnvironment()))
                        .collect(Collectors.toMap(ResourceConfigurationTemplate::getResourceType, Function.identity()));

        return command
                .getResourceTypes()
                .stream()
                .map(resourceTypeTemplateMap::get)
                .map(generateResourceConfiguration(command.getService(), command.getEnvironment()))
                .map(resourceConfigurationRepository::save)
                .collect(Collectors.toList());
    }

    private Function<ResourceConfigurationTemplate<?>, ResourceConfiguration> generateResourceConfiguration(
            String service, Environment environment
    ) {
        return template -> {
            ProvisioningAccount provisioningAccount = provisionAccount(service, environment, template.getResourceType());
            return ResourceConfiguration
                    .builder()
                    .environment(environment)
                    .service(service)
                    .resourceConfigurationTemplate(template)
                    .provisioningAccount(provisioningAccount)
                    .build();
        };
    }

    private ProvisioningAccount provisionAccount(String service, Environment environment, ResourceType resourceType) {
        return provisioningAccountRegisters
                .stream()
                .filter(register -> register.supports(resourceType))
                .map(register -> register.register(service, environment))
                .findAny()
                .orElse(null);
    }
}
