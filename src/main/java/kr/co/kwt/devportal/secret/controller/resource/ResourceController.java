package kr.co.kwt.devportal.secret.controller.resource;

import kr.co.kwt.devportal.secret.model.resource.Resource;
import kr.co.kwt.devportal.secret.service.resource.ResourceService;
import kr.co.kwt.devportal.secret.service.resource.command.AddResourceCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ResourceController {

    private final ResourceService resourceService;

    @GetMapping("/resources")
    public List<Resource> getPresetResources() {
        return resourceService.getPresetResources();
    }

    @PostMapping("/resources")
    public String addPresetResource(@Validated @RequestBody AddResourceCommand command) {
        return resourceService
                .addPresetResource(command)
                .getId();
    }
}
