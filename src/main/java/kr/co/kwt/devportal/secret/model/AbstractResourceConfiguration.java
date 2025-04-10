package kr.co.kwt.devportal.secret.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import kr.co.kwt.devportal.secret.model.register.ProvisioningAccount;
import kr.co.kwt.devportal.secret.model.template.ResourceConfigurationTemplate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Getter
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "resourceType",
        visible = true  // resourceType에 값을 사용 후에 바인딩 하도록 설정
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = MySqlResourceConfiguration.class, name = "MYSQL"),
        @JsonSubTypes.Type(value = KafkaResourceConfiguration.class, name = "KAFKA"),
        @JsonSubTypes.Type(value = MongoResourceConfiguration.class, name = "MONGO"),
        @JsonSubTypes.Type(value = RedisResourceConfiguration.class, name = "REDIS")
})
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractResourceConfiguration<T> implements ResourceConfiguration<T> {

    protected String service;
    protected Environment environment;
    protected ResourceType resourceType;
    @DocumentReference
    protected ResourceConfigurationTemplate<T> resourceConfigurationTemplate;
    protected ProvisioningAccount provisioningAccount;

}
