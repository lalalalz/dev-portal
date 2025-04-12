package kr.co.kwt.devportal.secret.v2.model.template;

import kr.co.kwt.devportal.secret.v2.model.Environment;
import kr.co.kwt.devportal.secret.v2.model.ResourceType;
import kr.co.kwt.devportal.secret.v2.model.template.properties.DataSourceProperties;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.TypeAlias;

@Getter
@TypeAlias("dataSourceResourceConfigurationTemplate")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DataSourceResourceConfigurationTemplate extends ResourceConfigurationTemplate<DataSourceProperties> {

    @Builder
    public DataSourceResourceConfigurationTemplate(Environment environment, DataSourceProperties properties) {
        super(null, environment, ResourceType.MYSQL, properties, ResourceType.MYSQL.isSupportsProvisionAccount());
    }
}
