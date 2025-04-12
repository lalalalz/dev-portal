package kr.co.kwt.devportal.secret.v2.controller;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import kr.co.kwt.devportal.secret.v2.model.Environment;
import kr.co.kwt.devportal.secret.v2.service.ResourceConfigurationResolver;
import kr.co.kwt.devportal.secret.v2.service.ResourceConfigurationService;
import kr.co.kwt.devportal.secret.v2.service.command.AddResourceConfigurationCommand;
import kr.co.kwt.devportal.secret.v2.service.command.GetResourceConfigurationProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class ResourceConfigurationController {

    private final ResourceConfigurationService resourceConfigurationService;
    private final ResourceConfigurationResolver resourceConfigurationResolver;

    @GetMapping("/")
    public String resourceConfigurationView() {
        return "secret/view";
    }

    @ResponseBody
    @GetMapping("/resource-configurations")
    public GetResourceConfigurationProperties getResourceConfigurations(
            @RequestParam("service") @NotBlank String service,
            @RequestParam("environment") @NotNull Environment environment
    ) {
        return new GetResourceConfigurationProperties(
                resourceConfigurationResolver.resolve(
                        resourceConfigurationService.getResourceConfigurations(service, environment)));
    }

    @ResponseBody
    @PostMapping("/resource-configurations")
    public GetResourceConfigurationProperties addResourceConfigurations(
            @RequestBody AddResourceConfigurationCommand command
    ) {
        return new GetResourceConfigurationProperties(
                resourceConfigurationResolver.resolve(
                        resourceConfigurationService.addResourceConfiguration(command)));
    }
}
