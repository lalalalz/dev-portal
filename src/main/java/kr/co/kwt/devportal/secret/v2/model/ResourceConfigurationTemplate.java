package kr.co.kwt.devportal.secret.v2.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.lang.NonNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Document(collection = "resourceConfigurationTemplates_v2")
public class ResourceConfigurationTemplate<T> {

    @Id
    private String id;
    private Environment environment;
    private ResourceType resourceType;
    private T properties;
    private boolean supportsAccountProvisioning;

    @Builder
    public ResourceConfigurationTemplate(
            String id,
            @NonNull Environment environment,
            @NonNull ResourceType resourceType,
            @NonNull T properties,
            boolean supportsAccountProvisioning
    ) {
        this.id = id;
        this.environment = environment;
        this.resourceType = resourceType;
        this.properties = properties;
        this.supportsAccountProvisioning = supportsAccountProvisioning;
    }
}



