package kr.co.kwt.devportal.secret.model.template;

import kr.co.kwt.devportal.secret.model.ResourceType;
import kr.co.kwt.devportal.secret.model.property.RedisProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.lang.NonNull;

import static lombok.AccessLevel.PROTECTED;

@Getter
@TypeAlias("datasourceConfigurationTemplate")
@Document(collection = "resourceConfigurationTemplates")
@NoArgsConstructor(access = PROTECTED)
public class RedisResourceConfigurationTemplate extends AbstractResourceConfigurationTemplate<RedisProperties> {

    @Id
    private String id;

    public RedisResourceConfigurationTemplate(@NonNull RedisProperties properties) {
        super(properties, ResourceType.REDIS, false);
    }
}
