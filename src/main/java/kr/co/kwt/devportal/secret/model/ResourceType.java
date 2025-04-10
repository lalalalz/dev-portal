package kr.co.kwt.devportal.secret.model;

import kr.co.kwt.devportal.secret.model.template.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResourceType {

    MYSQL(MySqlResourceConfiguration.class, DataSourceResourceConfigurationTemplate.class),
    REDIS(RedisResourceConfiguration.class, RedisResourceConfigurationTemplate.class),
    MONGO(MongoResourceConfiguration.class, MongoResourceConfigurationTemplate.class),
    KAFKA(KafkaResourceConfiguration.class, KafkaResourceConfigurationTemplate.class),
    ;

    private final Class<? extends ResourceConfiguration<?>> resourceConfigurationClass;
    private final Class<? extends ResourceConfigurationTemplate<?>> resourceConfigurationTemplateClass;
}
