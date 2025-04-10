package kr.co.kwt.devportal.secret.service.command;

import kr.co.kwt.devportal.secret.model.property.MongoProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AddMongoResourceConfigurationCommand extends AddResourceConfigurationTemplateCommand<MongoProperties> {
}
