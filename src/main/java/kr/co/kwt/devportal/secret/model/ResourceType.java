package kr.co.kwt.devportal.secret.model;

import kr.co.kwt.devportal.secret.model.template.DataSourceResourceConfigurationTemplate;
import kr.co.kwt.devportal.secret.model.template.RedisResourceConfigurationTemplate;
import kr.co.kwt.devportal.secret.model.template.ResourceConfigurationTemplate;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResourceType {

    MYSQL(MySqlResourceConfiguration.class, DataSourceResourceConfigurationTemplate.class),
    REDIS(RedisResourceConfiguration.class, RedisResourceConfigurationTemplate.class),
    ;

    private final Class<? extends ResourceConfiguration<?>> resourceConfigurationClass;
    private final Class<? extends ResourceConfigurationTemplate<?>> resourceConfigurationTemplateClass;
}
