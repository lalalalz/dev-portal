package kr.co.kwt.devportal.secret.model.template;

import kr.co.kwt.devportal.secret.model.Environment;
import kr.co.kwt.devportal.secret.model.ResourceType;
import kr.co.kwt.devportal.secret.model.property.KafkaProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.lang.NonNull;

import static lombok.AccessLevel.PROTECTED;

@Getter
@TypeAlias("kafkaConfigurationTemplate")
@Document(collection = "resourceConfigurationTemplates")
@NoArgsConstructor(access = PROTECTED)
public class KafkaResourceConfigurationTemplate extends AbstractResourceConfigurationTemplate<KafkaProperties> {

    @Id
    private String id;

    public KafkaResourceConfigurationTemplate(@NonNull Environment environment, @NonNull KafkaProperties properties) {
        super(environment, ResourceType.KAFKA, properties, false);
    }
}
