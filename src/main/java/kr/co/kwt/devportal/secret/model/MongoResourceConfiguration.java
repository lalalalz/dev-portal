package kr.co.kwt.devportal.secret.model;

import kr.co.kwt.devportal.secret.model.property.MongoProperties;
import kr.co.kwt.devportal.secret.model.template.ResourceConfigurationTemplate;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.lang.NonNull;

import static lombok.AccessLevel.PROTECTED;

@Getter
@TypeAlias("mongoConfiguration")
@NoArgsConstructor(access = PROTECTED)
@Document(collection = "resourceConfigurations")
public class MongoResourceConfiguration extends AbstractResourceConfiguration<MongoProperties> {

    @Id
    private String id;

    @Builder
    public MongoResourceConfiguration(
            @NonNull String service,
            @NonNull Environment environment,
            @NonNull ResourceConfigurationTemplate<MongoProperties> resourceConfigurationTemplate
    ) {
        super(service, environment, ResourceType.REDIS, resourceConfigurationTemplate, null);
    }
}
