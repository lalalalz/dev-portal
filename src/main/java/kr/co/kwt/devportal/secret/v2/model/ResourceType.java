package kr.co.kwt.devportal.secret.v2.model;

import kr.co.kwt.devportal.secret.v2.model.template.DataSourceResourceConfigurationTemplate;
import kr.co.kwt.devportal.secret.v2.model.template.RedisResourceConfigurationTemplate;
import kr.co.kwt.devportal.secret.v2.model.template.ResourceConfigurationTemplate;
import kr.co.kwt.devportal.secret.v2.model.template.properties.DataSourceProperties;
import kr.co.kwt.devportal.secret.v2.model.template.properties.RedisProperties;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResourceType {

    MYSQL(true, DataSourceProperties.class, DataSourceResourceConfigurationTemplate.class),
    REDIS(false, RedisProperties.class, RedisResourceConfigurationTemplate.class),
//    MONGO,
//    KAFKA,
    ;

    private final boolean supportsProvisionAccount;
    private final Class<?> propertiesClass;
    private final Class<? extends ResourceConfigurationTemplate<?>> resourceConfigurationTemplateClass;
}
