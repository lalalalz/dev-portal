package kr.co.kwt.devportal.secret.v2.service.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import kr.co.kwt.devportal.secret.v2.model.Environment;
import kr.co.kwt.devportal.secret.v2.model.ResourceType;
import lombok.Getter;

import java.util.List;

@Getter
public class AddResourceConfigurationCommand {

    @NotBlank
    private String service;
    @NotNull
    private Environment environment;
    @Size(min = 1)
    private List<ResourceType> resourceTypes;

}
