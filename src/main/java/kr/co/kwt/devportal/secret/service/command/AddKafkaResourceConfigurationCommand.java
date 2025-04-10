package kr.co.kwt.devportal.secret.service.command;

import kr.co.kwt.devportal.secret.model.property.KafkaProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AddKafkaResourceConfigurationCommand extends AddResourceConfigurationTemplateCommand<KafkaProperties> {
}
