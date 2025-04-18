package kr.co.kwt.devportal.secret.v2.service;

import kr.co.kwt.devportal.secret.v2.model.ResourceConfigurationTemplate;
import kr.co.kwt.devportal.secret.v2.repository.ResourceConfigurationTemplateRepository;
import kr.co.kwt.devportal.secret.v2.service.command.AddResourceConfigurationTemplateCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResourceConfigurationTemplateService {

    private final ResourceConfigurationTemplateRepository resourceConfigurationTemplateRepository;

    public List<ResourceConfigurationTemplate<?>> searchResourceConfigurationTemplates() {
        List<ResourceConfigurationTemplate<?>> all = resourceConfigurationTemplateRepository.findAll();
        return all;
    }

    public ResourceConfigurationTemplate<?> addResourceConfigurationTemplate(
            AddResourceConfigurationTemplateCommand<?> command
    ) {
        try {
            return resourceConfigurationTemplateRepository.save(command.toResourceConfigurationTemplate());
        }
        catch (Exception e) {
            throw new AddResourceConfigurationTemplateException(e);
        }
    }
}
