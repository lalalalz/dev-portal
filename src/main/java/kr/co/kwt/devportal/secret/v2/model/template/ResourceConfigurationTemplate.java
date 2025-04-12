package kr.co.kwt.devportal.secret.v2.model.template;

import kr.co.kwt.devportal.secret.v2.model.Environment;
import kr.co.kwt.devportal.secret.v2.model.ResourceType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.lang.NonNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Document(collection = "resourceConfigurationTemplates_v2")
public abstract class ResourceConfigurationTemplate<T> {

    @Id
    protected String id;
    protected Environment environment;
    protected ResourceType resourceType;
    protected T properties;
    protected boolean supportsAccountProvisioning;

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



