package kr.co.kwt.devportal.secret.model.template;

import kr.co.kwt.devportal.secret.model.Environment;
import kr.co.kwt.devportal.secret.model.ResourceType;
import kr.co.kwt.devportal.secret.model.property.DataSourceProperties;
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
public class DataSourceResourceConfigurationTemplate extends AbstractResourceConfigurationTemplate<DataSourceProperties> {

    @Id
    private String id;

    public DataSourceResourceConfigurationTemplate(
            @NonNull Environment environment,
            @NonNull DataSourceProperties properties
    ) {
        super(environment, ResourceType.MYSQL, properties, true);
    }
}
