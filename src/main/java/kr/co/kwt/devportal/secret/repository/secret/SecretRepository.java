package kr.co.kwt.devportal.secret.repository.secret;

import kr.co.kwt.devportal.secret.model.secret.Secret;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface SecretRepository extends MongoRepository<Secret, String> {

    Optional<Secret> findByService(String service);
}
