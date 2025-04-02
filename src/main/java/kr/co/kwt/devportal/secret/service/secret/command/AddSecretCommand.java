package kr.co.kwt.devportal.secret.service.secret.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import kr.co.kwt.devportal.secret.model.resource.ResourceType;
import lombok.Value;

import java.util.List;

@Value
public class AddSecretCommand {

    @NotBlank
    String service;
    @Size(min = 1)
    List<ResourceType> resourceTypes;

}
