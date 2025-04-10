package kr.co.kwt.devportal.secret.model.template;

import kr.co.kwt.devportal.secret.model.Environment;
import kr.co.kwt.devportal.secret.model.ResourceType;
import kr.co.kwt.devportal.secret.model.property.MongoProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.lang.NonNull;

import static lombok.AccessLevel.PROTECTED;

@Getter
@TypeAlias("mongoConfigurationTemplate")
@Document(collection = "resourceConfigurationTemplates")
@NoArgsConstructor(access = PROTECTED)
public class MongoResourceConfigurationTemplate extends AbstractResourceConfigurationTemplate<MongoProperties> {

    @Id
    private String id;

    public MongoResourceConfigurationTemplate(@NonNull Environment environment, @NonNull MongoProperties properties) {
        super(environment, ResourceType.MONGO, properties, false);
    }
}
