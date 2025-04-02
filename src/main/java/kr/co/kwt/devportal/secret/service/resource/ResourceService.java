package kr.co.kwt.devportal.secret.service.resource;

import kr.co.kwt.devportal.secret.model.resource.*;
import kr.co.kwt.devportal.secret.model.resource.provisioner.ResourceAccountProvisioner;
import kr.co.kwt.devportal.secret.repository.resource.ResourceRepository;
import kr.co.kwt.devportal.secret.service.resource.command.AddMySQLResourceCommand;
import kr.co.kwt.devportal.secret.service.resource.command.AddRedisResourceCommand;
import kr.co.kwt.devportal.secret.service.resource.command.AddResourceCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static kr.co.kwt.devportal.secret.model.resource.provisioner.ResourceAccountProvisioner.UsernameAndPassword;

@Service
@RequiredArgsConstructor
public class ResourceService {

    private final ResourceRepository resourceRepository;
    private final List<ResourceAccountProvisioner> resourceAccountProvisioners;
    private final Resource.ResourceProvider resourceProvider;

    /**
     * 프리셋 리소스 조회
     *
     * @return
     */
    public List<Resource> getPresetResources() {
        return resourceRepository.findByIsPresetTrue();
    }

    /**
     * 프리셋 리소스 추가
     *
     * @param addResourceCommand
     * @return
     */
    public Resource addPresetResource(AddResourceCommand addResourceCommand) {
        Resource resource;

        switch (addResourceCommand.getResourceType()) {
            case MYSQL -> {
                AddMySQLResourceCommand command = (AddMySQLResourceCommand) addResourceCommand;
                resource = MySQLResource
                        .builder()
                        .host(command.getHost())
                        .port(command.getPort())
                        .isPreset(true)
                        .build();
            }
            case REDIS -> {
                AddRedisResourceCommand command = (AddRedisResourceCommand) addResourceCommand;
                resource = RedisResource
                        .builder()
                        .username(command.getUsername())
                        .password(command.getPassword())
                        .host(command.getHost())
                        .port(command.getPort())
                        .isPreset(true)
                        .build();
            }
            default -> throw new InvalidResourceTypeException();
        }

        return resourceRepository.save(resource);
    }

    /**
     * 시크릿의 리소스를 조회한다.
     *
     * @param service
     * @param resourceTypes
     * @return
     */
    public List<Resource> getSecretResources(@NonNull String service, @NonNull List<ResourceType> resourceTypes) {
        return resourceTypes
                .stream()
                .map(type -> getResourceWithProvisioningAccount(service, type))
                .toList();
    }

    private Resource getResourceWithProvisioningAccount(String service, ResourceType resourceType) {
        Map<ResourceType, Resource> resourceTypeResourceMap = resourceProvider.getResourceTypeResourceMap();

        if (!resourceTypeResourceMap.containsKey(resourceType)) {
            throw new InvalidResourceTypeException();
        }

        Resource resource = resourceTypeResourceMap.get(resourceType);

        if (resourceType.isSupportsProvisionAccount()) {
            return provisionAccount(service, resource);
        }

        return resource;
    }

    /**
     * 계정 프로비저닝
     *
     * @param resource
     * @return
     */
    public Resource provisionAccount(@NonNull String service, @NonNull Resource resource) {
        if ((resource instanceof CloneableResource) &&
                resource.getResourceType().isSupportsProvisionAccount()
        ) {
            UsernameAndPassword usernameAndPassword = doProvisionAccount(service, resource);
            return resourceRepository.save(((CloneableResource) resource).cloneWithAccount(usernameAndPassword));
        }

        return resource;
    }

    private UsernameAndPassword doProvisionAccount(String service, Resource resource) {
        return resourceAccountProvisioners
                .stream()
                .filter(provisioner -> provisioner.isSupported(resource.getResourceType()))
                .findFirst()
                .orElseThrow(UnsupportedProvisioningAccountResourceException::new)
                .provision(service, resource.getResourceType());
    }
}
