package kr.co.kwt.devportal.secret.model;

import kr.co.kwt.devportal.secret.model.template.ResourceConfigurationTemplate;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.lang.NonNull;

import static lombok.AccessLevel.PROTECTED;

@Getter
@TypeAlias("redisConfiguration")
@NoArgsConstructor(access = PROTECTED)
@Document(collection = "resourceConfigurations")
public class RedisResourceConfiguration extends AbstractResourceConfiguration<RedisProperties> {

    @Id
    private String id;

    @Builder
    public RedisResourceConfiguration(
            @NonNull String service,
            @NonNull ResourceConfigurationTemplate<RedisProperties> resourceConfigurationTemplate
    ) {
        super(service, ResourceType.REDIS, resourceConfigurationTemplate, null);
    }
}
