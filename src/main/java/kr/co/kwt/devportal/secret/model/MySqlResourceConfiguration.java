package kr.co.kwt.devportal.secret.model;

import kr.co.kwt.devportal.secret.model.property.DataSourceProperties;
import kr.co.kwt.devportal.secret.model.register.ProvisioningAccount;
import kr.co.kwt.devportal.secret.model.template.ResourceConfigurationTemplate;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.lang.NonNull;

import static lombok.AccessLevel.PROTECTED;

@Getter
@TypeAlias("datasourceConfiguration")
@NoArgsConstructor(access = PROTECTED)
@Document(collection = "resourceConfigurations")
public class MySqlResourceConfiguration extends AbstractResourceConfiguration<DataSourceProperties> {

    @Id
    private String id;

    @Builder
    public MySqlResourceConfiguration(
            @NonNull String service,
            @NonNull Environment environment,
            @NonNull ResourceConfigurationTemplate<DataSourceProperties> resourceConfigurationTemplate,
            @NonNull ProvisioningAccount provisioningAccount
    ) {
        super(service, environment, ResourceType.MYSQL, resourceConfigurationTemplate, provisioningAccount);
    }
}
