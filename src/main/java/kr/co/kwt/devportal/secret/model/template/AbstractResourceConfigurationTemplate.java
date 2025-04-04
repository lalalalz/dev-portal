package kr.co.kwt.devportal.secret.model.template;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import kr.co.kwt.devportal.secret.model.ResourceType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "resourceType"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = DataSourceResourceConfigurationTemplate.class, name = "MYSQL"),
        @JsonSubTypes.Type(value = RedisResourceConfigurationTemplate.class, name = "REDIS")
})
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "resourceConfigurationTemplates")
public abstract class AbstractResourceConfigurationTemplate<T> implements ResourceConfigurationTemplate<T> {

    private T properties;
    private ResourceType resourceType;
    private boolean supportsAccountProvisioning;
}
