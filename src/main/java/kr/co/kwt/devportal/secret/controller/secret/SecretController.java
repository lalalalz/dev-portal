package kr.co.kwt.devportal.secret.controller.secret;

import jakarta.validation.constraints.NotBlank;
import kr.co.kwt.devportal.secret.model.secret.Secret;
import kr.co.kwt.devportal.secret.service.secret.SecretService;
import kr.co.kwt.devportal.secret.service.secret.command.AddSecretCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class SecretController {

    private final SecretService secretService;

    @GetMapping("/secrets")
    public Secret getSecrets(
            @Validated
            @NotBlank
            @RequestParam("service") String service
    ) {
        return secretService.getSecret(service);
    }

    @PostMapping("/secrets")
    public Secret addSecret(@Validated @RequestBody AddSecretCommand command) {
        return secretService.addSecret(command);
    }
}
