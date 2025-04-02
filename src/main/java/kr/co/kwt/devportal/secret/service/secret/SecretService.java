package kr.co.kwt.devportal.secret.service.secret;

import kr.co.kwt.devportal.secret.model.secret.Environment;
import kr.co.kwt.devportal.secret.model.secret.Secret;
import kr.co.kwt.devportal.secret.repository.secret.SecretRepository;
import kr.co.kwt.devportal.secret.service.resource.ResourceService;
import kr.co.kwt.devportal.secret.service.secret.command.AddSecretCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecretService {

    @Value("${spring.profiles.active:default}")
    private Environment environment;
    private final ResourceService resourceService;
    private final SecretRepository secretRepository;

    public Secret addSecret(AddSecretCommand command) {
        if (secretRepository
                .findByService(command.getService())
                .isPresent()
        ) {
            throw new AlreadyExistsSecretException();
        }

        return secretRepository
                .save(Secret
                        .builder()
                        .service(command.getService())
                        .environment(environment)
                        .resources(resourceService.getSecretResources(
                                command.getService(),
                                command.getResourceTypes()))
                        .build());
    }

    public Secret getSecret(String service) {
        return secretRepository
                .findByService(service)
                .orElseThrow(NoSuchSecretException::new);
    }
}
