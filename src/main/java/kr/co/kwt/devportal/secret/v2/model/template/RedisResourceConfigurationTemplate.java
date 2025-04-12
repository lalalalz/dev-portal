package kr.co.kwt.devportal.secret.v2.model.template;

import kr.co.kwt.devportal.secret.v2.model.Environment;
import kr.co.kwt.devportal.secret.v2.model.ResourceType;
import kr.co.kwt.devportal.secret.v2.model.template.properties.RedisProperties;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.TypeAlias;

@Getter
@TypeAlias("redisResourceConfigurationTemplate")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RedisResourceConfigurationTemplate extends ResourceConfigurationTemplate<RedisProperties> {

    @Builder
    public RedisResourceConfigurationTemplate(Environment environment, RedisProperties properties) {
        super(null, environment, ResourceType.REDIS, properties, ResourceType.REDIS.isSupportsProvisionAccount());
    }
}
