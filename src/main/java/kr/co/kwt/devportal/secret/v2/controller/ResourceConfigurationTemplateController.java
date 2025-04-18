package kr.co.kwt.devportal.secret.v2.controller;

import kr.co.kwt.devportal.secret.v2.model.ResourceConfigurationTemplate;
import kr.co.kwt.devportal.secret.v2.service.ResourceConfigurationTemplateService;
import kr.co.kwt.devportal.secret.v2.service.command.AddResourceConfigurationTemplateCommand;
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
        return resourceConfigurationTemplateService.searchResourceConfigurationTemplates();
    }

    @PostMapping("/resource-configuration/templates")
    public ResourceConfigurationTemplate<?> addResourceConfigurationTemplate(
            @RequestBody AddResourceConfigurationTemplateCommand<?> command
    ) {
        return resourceConfigurationTemplateService.addResourceConfigurationTemplate(command);
    }
}
