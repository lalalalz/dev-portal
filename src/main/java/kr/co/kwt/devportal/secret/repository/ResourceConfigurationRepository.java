package kr.co.kwt.devportal.secret.repository;

import kr.co.kwt.devportal.secret.model.Environment;
import kr.co.kwt.devportal.secret.model.ResourceConfiguration;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ResourceConfigurationRepository extends MongoRepository<ResourceConfiguration<?>, String> {

    List<ResourceConfiguration<?>> findAllByServiceAndEnvironment(String service, Environment environment);
}
