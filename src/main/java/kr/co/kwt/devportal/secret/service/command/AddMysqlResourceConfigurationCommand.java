package kr.co.kwt.devportal.secret.service.command;

import kr.co.kwt.devportal.secret.model.property.DataSourceProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AddMysqlResourceConfigurationCommand extends AddResourceConfigurationTemplateCommand<DataSourceProperties> {
}
