package kr.co.kwt.devportal.secret.service.command;

import kr.co.kwt.devportal.secret.model.property.RedisProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AddRedisResourceConfigurationCommand extends AddResourceConfigurationTemplateCommand<RedisProperties> {
}
