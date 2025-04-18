package kr.co.kwt.devportal.secret.v2.repository;

import kr.co.kwt.devportal.secret.v2.model.ResourceConfigurationTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceConfigurationTemplateRepository extends MongoRepository<ResourceConfigurationTemplate<?>, String> {
}
