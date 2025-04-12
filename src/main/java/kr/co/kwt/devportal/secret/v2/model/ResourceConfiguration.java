package kr.co.kwt.devportal.secret.v2.model;

import kr.co.kwt.devportal.secret.v2.model.register.ProvisioningAccount;
import kr.co.kwt.devportal.secret.v2.model.template.ResourceConfigurationTemplate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

@Getter
@TypeAlias("resourceConfiguration")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Document(collection = "resourceConfigurations_v2")
public class ResourceConfiguration {

    @Id
    private String id;
    private String service;
    private Environment environment;
    @DocumentReference
    private ResourceConfigurationTemplate<?> resourceConfigurationTemplate;
    private ProvisioningAccount provisioningAccount;

    @Builder
    public ResourceConfiguration(
            @NonNull String service,
            @NonNull Environment environment,
            @NonNull ResourceConfigurationTemplate<?> resourceConfigurationTemplate,
            @Nullable ProvisioningAccount provisioningAccount
    ) {
        this.service = service;
        this.environment = environment;
        this.resourceConfigurationTemplate = resourceConfigurationTemplate;
        this.provisioningAccount = provisioningAccount;
    }
}
