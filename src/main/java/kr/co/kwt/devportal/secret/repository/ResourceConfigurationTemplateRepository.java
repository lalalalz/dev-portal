package kr.co.kwt.devportal.secret.repository;

import kr.co.kwt.devportal.secret.model.template.ResourceConfigurationTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ResourceConfigurationTemplateRepository extends MongoRepository<ResourceConfigurationTemplate<?>, String> {
}
