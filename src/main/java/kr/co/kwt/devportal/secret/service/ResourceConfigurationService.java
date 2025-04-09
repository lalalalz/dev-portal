package kr.co.kwt.devportal.secret.service;

import jakarta.validation.constraints.NotBlank;
import kr.co.kwt.devportal.secret.model.ResourceConfiguration;
import kr.co.kwt.devportal.secret.model.ResourceType;
import kr.co.kwt.devportal.secret.model.register.ProvisioningAccount;
import kr.co.kwt.devportal.secret.model.register.ProvisioningAccountRegister;
import kr.co.kwt.devportal.secret.model.template.ResourceConfigurationTemplate;
import kr.co.kwt.devportal.secret.repository.ResourceConfigurationRepository;
import kr.co.kwt.devportal.secret.service.command.AddResourceConfigurationsCommand;
import kr.co.kwt.devportal.secret.service.command.SearchFlatResourceConfigurationProperties;
import kr.co.kwt.devportal.secret.utility.ObjectFlattener;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

@Service
@RequiredArgsConstructor
public class ResourceConfigurationService {

    private final ObjectFlattener objectFlattener;
    private final List<ProvisioningAccountRegister> provisioningAccountRegisters;
    private final ResourceConfigurationRepository resourceConfigurationRepository;
    private final ResourceConfigurationTemplateService resourceConfigurationTemplateService;

    /**
     * 리소스 설정값 조회
     *
     * @return
     */
    public SearchFlatResourceConfigurationProperties searchFlatResourceConfigurationProperties(@NotBlank String service) {
        return new SearchFlatResourceConfigurationProperties(
                resourceConfigurationRepository
                        .findAllByService(service)
                        .stream()
                        .map(this::flattenResourceConfigurationProperties)
                        .collect(HashMap::new, Map::putAll, Map::putAll));
    }

    private Map<String, Object> flattenResourceConfigurationProperties(ResourceConfiguration<?> resourceConfiguration) {
        return createFlattenedPropertiesWithPrefix(resourceConfiguration);
    }

    private Map<String, Object> createFlattenedPropertiesWithPrefix(ResourceConfiguration<?> resourceConfiguration) {
        ResourceConfigurationTemplate<?> template = resourceConfiguration.getResourceConfigurationTemplate();
        String resourceTypePrefix = template.getResourceType().name().toLowerCase();

        // 1. 기본 프로퍼티 맵 생성
        Map<String, Object> flattenedProperties = objectFlattener.flattenMap(template.getProperties());

        // 2. 프로비저닝 계정 프로퍼티 추가 (필요시)
        if (template.isSupportsAccountProvisioning()) {
            Map<String, Object> accountProperties = objectFlattener.flattenMap(resourceConfiguration.getProvisioningAccount());
            flattenedProperties.putAll(
                    accountProperties
                            .entrySet()
                            .stream()
                            .filter(entry -> entry.getValue() != null)
                            .collect(toMap(Map.Entry::getKey, Map.Entry::getValue)));
        }

        // 3. null 값 필터링하고 프리픽스 추가하여 반환
        return flattenedProperties
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue() != null)
                .collect(toMap(
                        entry -> resourceTypePrefix + "." + entry.getKey(),
                        Map.Entry::getValue
                ));
    }

    /**
     * 리소스 설정 추가
     *
     * @param command
     * @return
     */
    public List<ResourceConfiguration<?>> addResourceConfigurations(AddResourceConfigurationsCommand command) {
        String service = command.getService();
        validateServiceNotAlreadyAdded(service);

        return command
                .getResourceTypes()
                .stream()
                .map(resourceType -> createResourceConfiguration(service, resourceType))
                .collect(toList());
    }

    private void validateServiceNotAlreadyAdded(String service) {
        List<ResourceConfiguration<?>> allByService = resourceConfigurationRepository
                .findAllByService(service);
        if (!resourceConfigurationRepository
                .findAllByService(service)
                .isEmpty()
        ) {
            throw new AlreadyAddedResourceConfiguration();
        }
    }

    private ResourceConfiguration<?> createResourceConfiguration(String service, ResourceType resourceType) {
        ResourceConfigurationTemplate<?> template = getTemplateForResourceType(resourceType);
        ResourceConfiguration<?> configuration = instantiateResourceConfiguration(service, template);
        return resourceConfigurationRepository.save(configuration);
    }

    private ResourceConfigurationTemplate<?> getTemplateForResourceType(ResourceType resourceType) {
        Map<ResourceType, ResourceConfigurationTemplate<?>> templateMap = resourceConfigurationTemplateService
                .getResourceTypeResourceConfigurationTemplateMap();

        if (!templateMap.containsKey(resourceType)) {
            throw new UnsupportedResourceTypeException();
        }

        return templateMap.get(resourceType);
    }

    private ResourceConfiguration<?> instantiateResourceConfiguration(
            String service, ResourceConfigurationTemplate<?> template
    ) {
        return template.isSupportsAccountProvisioning()
                ? instantiateWithProvisioningAccount(service, template)
                : instantiateWithoutProvisioningAccount(service, template);
    }

    private ResourceConfiguration<?> instantiateWithProvisioningAccount(
            String service, ResourceConfigurationTemplate<?> template
    ) {
        try {
            Class<?> configClass = template.getResourceType().getResourceConfigurationClass();
            ProvisioningAccount account = provisionAccountFor(service, template.getResourceType());

            return (ResourceConfiguration<?>) configClass
                    .getDeclaredConstructor(String.class, ResourceConfigurationTemplate.class, ProvisioningAccount.class)
                    .newInstance(service, template, account);
        }
        catch (Exception e) {
            throw new CreateResourceConfigurationException(e);
        }
    }

    private ResourceConfiguration<?> instantiateWithoutProvisioningAccount(
            String service, ResourceConfigurationTemplate<?> template
    ) {
        try {
            Class<?> configClass = template.getResourceType().getResourceConfigurationClass();

            return (ResourceConfiguration<?>) configClass
                    .getDeclaredConstructor(String.class, ResourceConfigurationTemplate.class)
                    .newInstance(service, template);
        }
        catch (Exception e) {
            throw new CreateResourceConfigurationException(e);
        }
    }

    private ProvisioningAccount provisionAccountFor(String service, ResourceType resourceType) {
        return provisioningAccountRegisters
                .stream()
                .filter(register -> register.supports(resourceType))
                .findAny()
                .map(register -> register.register(service))
                .orElseThrow(UnsupportedResourceTypeException::new);
    }
}
