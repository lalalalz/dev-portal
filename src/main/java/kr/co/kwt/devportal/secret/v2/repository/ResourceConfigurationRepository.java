package kr.co.kwt.devportal.secret.v2.repository;

import kr.co.kwt.devportal.secret.v2.model.Environment;
import kr.co.kwt.devportal.secret.v2.model.ResourceConfiguration;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResourceConfigurationRepository extends MongoRepository<ResourceConfiguration, String> {
    List<ResourceConfiguration> findAllByServiceAndEnvironment(String service, Environment environment);
}
