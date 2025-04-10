package kr.co.kwt.devportal.secret.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import kr.co.kwt.devportal.secret.model.Environment;
import kr.co.kwt.devportal.secret.model.ResourceConfiguration;
import kr.co.kwt.devportal.secret.service.ResourceConfigurationService;
import kr.co.kwt.devportal.secret.service.command.AddResourceConfigurationsCommand;
import kr.co.kwt.devportal.secret.service.command.SearchFlatResourceConfigurationProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ResourceConfigurationController {

    private final ResourceConfigurationService resourceConfigurationService;

    @GetMapping("/")
    public String resourceConfigurationView() {
        return "secret/view";
    }

    @ResponseBody
    @GetMapping("/resource-configuration/properties/flat")
    public SearchFlatResourceConfigurationProperties getFlatResourceConfigurationProperties(
            @RequestParam("service") @NotBlank String service,
            @RequestParam("environment") @NotNull Environment environment
    ) {
        return resourceConfigurationService.searchFlatResourceConfigurationProperties(service, environment);
    }

    @ResponseBody
    @PostMapping("/resource-configuration")
    public List<? extends ResourceConfiguration<?>> addResourceConfigurations(
            @Valid @RequestBody AddResourceConfigurationsCommand command
    ) {
        return resourceConfigurationService.addResourceConfigurations(command);
    }
}
