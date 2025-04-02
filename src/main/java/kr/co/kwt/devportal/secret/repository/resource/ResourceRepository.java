package kr.co.kwt.devportal.secret.repository.resource;

import kr.co.kwt.devportal.secret.model.resource.Resource;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ResourceRepository extends MongoRepository<Resource, String> {

    @Query("{ 'isPreset': true }")
    List<Resource> findByIsPresetTrue();
}
