package kr.co.kwt.devportal.secret.controller;

import jakarta.validation.Valid;
import kr.co.kwt.devportal.secret.model.template.ResourceConfigurationTemplate;
import kr.co.kwt.devportal.secret.service.ResourceConfigurationTemplateService;
import kr.co.kwt.devportal.secret.service.command.AddResourceConfigurationTemplateCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ResourceConfigurationTemplateController {

    private final ResourceConfigurationTemplateService resourceConfigurationTemplateService;

    @GetMapping("/resource-configuration/templates")
    public List<ResourceConfigurationTemplate<?>> getResourceConfigurationTemplates() {
        return resourceConfigurationTemplateService.getResourceConfigurationTemplates();
    }

    @PostMapping("/resource-configuration/templates")
    public String addResourceConfigurationTemplate(
            @Valid @RequestBody AddResourceConfigurationTemplateCommand<?> command
    ) {
        return resourceConfigurationTemplateService.addResourcePropertyTemplate(command);
    }
}
