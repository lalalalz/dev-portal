package kr.co.kwt.devportal.secret.service.secret;

import kr.co.kwt.devportal.secret.model.resource.ResourceType;
import kr.co.kwt.devportal.secret.model.secret.Secret;
import kr.co.kwt.devportal.secret.service.secret.command.AddSecretCommand;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SecretServiceTest {

    @Autowired
    private SecretService secretService;

    @Test
    void 시크릿을_생성한다() {
        // given
        AddSecretCommand command = new AddSecretCommand("시크릿 생성 테스트 서비스", List.of(ResourceType.MYSQL));

        // when
        Secret secret = secretService.addSecret(command);

        // then
        assertThat(secret.getService()).isEqualTo(command.getService());
    }

}