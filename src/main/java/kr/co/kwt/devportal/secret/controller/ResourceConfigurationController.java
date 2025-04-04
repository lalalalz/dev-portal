package kr.co.kwt.devportal.secret.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import kr.co.kwt.devportal.secret.model.ResourceConfiguration;
import kr.co.kwt.devportal.secret.service.ResourceConfigurationService;
import kr.co.kwt.devportal.secret.service.command.AddResourceConfigurationsCommand;
import kr.co.kwt.devportal.secret.service.command.SearchFlatResourceConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ResourceConfigurationController {

    private final ResourceConfigurationService resourceConfigurationService;

    @GetMapping("/resource-configuration/view")
    public String resourceConfigurationView() {
        return "secret/view";
    }

    @ResponseBody
    @GetMapping("/resource-configuration/flat")
    public List<SearchFlatResourceConfiguration> getFlatResourceConfigurations(
            @RequestParam("service") @NotBlank String service
    ) {
        return resourceConfigurationService.getFlatResourceConfigurations(service);
    }

    @ResponseBody
    @PostMapping("/resource-configuration")
    public List<? extends ResourceConfiguration<?>> addResourceConfigurations(
            @Valid @RequestBody AddResourceConfigurationsCommand command
    ) {
        return resourceConfigurationService.addResourceConfigurations(command);
    }
}
