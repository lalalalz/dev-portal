package kr.co.kwt.devportal.secret.v2.model;

import kr.co.kwt.devportal.secret.v2.model.properties.DataSourceProperties;
import kr.co.kwt.devportal.secret.v2.model.properties.RedisProperties;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResourceType {

    MYSQL(DataSourceProperties.class),
    REDIS(RedisProperties.class),
    MONGO(DataSourceProperties.class),
    KAFKA(DataSourceProperties.class),
    ;

    private final Class<?> propertiesClass;
}
