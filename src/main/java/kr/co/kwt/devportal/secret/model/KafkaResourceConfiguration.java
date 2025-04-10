package kr.co.kwt.devportal.secret.model;

import kr.co.kwt.devportal.secret.model.property.KafkaProperties;
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
@TypeAlias("kafkaConfiguration")
@NoArgsConstructor(access = PROTECTED)
@Document(collection = "resourceConfigurations")
public class KafkaResourceConfiguration extends AbstractResourceConfiguration<KafkaProperties> {

    @Id
    private String id;

    @Builder
    public KafkaResourceConfiguration(
            @NonNull String service,
            @NonNull Environment environment,
            @NonNull ResourceConfigurationTemplate<KafkaProperties> resourceConfigurationTemplate
    ) {
        super(service, environment, ResourceType.REDIS, resourceConfigurationTemplate, null);
    }
}
