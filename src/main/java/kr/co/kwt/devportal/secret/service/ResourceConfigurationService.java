package kr.co.kwt.devportal.secret.service;

import jakarta.validation.constraints.NotBlank;
import kr.co.kwt.devportal.secret.model.ResourceConfiguration;
import kr.co.kwt.devportal.secret.model.ResourceType;
import kr.co.kwt.devportal.secret.model.register.ProvisioningAccount;
import kr.co.kwt.devportal.secret.model.register.ProvisioningAccountRegister;
import kr.co.kwt.devportal.secret.model.template.ResourceConfigurationTemplate;
import kr.co.kwt.devportal.secret.repository.ResourceConfigurationRepository;
import kr.co.kwt.devportal.secret.service.command.AddResourceConfigurationsCommand;
import kr.co.kwt.devportal.secret.service.command.SearchFlatResourceConfiguration;
import kr.co.kwt.devportal.secret.utility.ObjectFlattener;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResourceConfigurationService {

    private final ObjectFlattener objectFlattener;
    private final List<ProvisioningAccountRegister> provisioningAccountRegisters;
    private final ResourceConfigurationRepository resourceConfigurationRepository;
    private final ResourceConfigurationTemplateService resourceConfigurationTemplateService;

    /**
     * 리소스 설정 조회
     *
     * @return
     */
    public List<SearchFlatResourceConfiguration> getFlatResourceConfigurations(@NotBlank String service) {
        return resourceConfigurationRepository
                .findAllByService(service)
                .stream()
                .map(objectFlattener::flatten)
                .map(SearchFlatResourceConfiguration::new)
                .toList();
    }

    /**
     * 리소스 설정 추가
     *
     * @param command
     * @return
     */
    public List<? extends ResourceConfiguration<?>> addResourceConfigurations(AddResourceConfigurationsCommand command) {
        Map<ResourceType, ResourceConfigurationTemplate<?>> templateMap = resourceConfigurationTemplateService
                .getResourceConfigurationTemplates()
                .stream()
                .collect(Collectors.toMap(ResourceConfigurationTemplate::getResourceType, Function.identity()));

        return command
                .getResourceTypes()
                .stream()
                .map(templateMap::get)
                .map(template -> createResourceConfiguration(command.getService(), template))
                .map(resourceConfigurationRepository::save)
                .toList();
    }

    private ResourceConfiguration<?> createResourceConfiguration(String service, ResourceConfigurationTemplate<?> template) {
        return template.isSupportsAccountProvisioning()
                ? createWithProvisioningAccount(service, template)
                : createWithoutProvisioningAccount(service, template);
    }

    private ResourceConfiguration<?> createWithProvisioningAccount(String service, ResourceConfigurationTemplate<?> template) {
        try {
            return template
                    .getResourceType()
                    .getResourceConfigurationClass()
                    .getDeclaredConstructor(String.class, ResourceConfigurationTemplate.class, ProvisioningAccount.class)
                    .newInstance(service, template, provisionAccount(service, template.getResourceType()));
        }
        catch (Exception e) {
            throw new CreateResourceConfigurationException();
        }
    }

    private ResourceConfiguration<?> createWithoutProvisioningAccount(String service, ResourceConfigurationTemplate<?> template) {
        try {
            return template
                    .getResourceType()
                    .getResourceConfigurationClass()
                    .getDeclaredConstructor(String.class, ResourceConfigurationTemplate.class)
                    .newInstance(service, template);
        }
        catch (Exception e) {
            throw new CreateResourceConfigurationException();
        }
    }

    private ProvisioningAccount provisionAccount(String service, ResourceType resourceType) {
        return provisioningAccountRegisters
                .stream()
                .filter(provisioningAccountRegister -> provisioningAccountRegister.supports(resourceType))
                .findAny()
                .map(provisioningAccountRegister -> provisioningAccountRegister.register(service, resourceType))
                .orElseThrow(UnsupportedResourceTypeException::new);
    }
}
