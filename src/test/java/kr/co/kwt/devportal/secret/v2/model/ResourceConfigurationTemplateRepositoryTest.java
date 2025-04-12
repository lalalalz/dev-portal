package kr.co.kwt.devportal.secret.v2.model;

import kr.co.kwt.devportal.secret.v2.model.template.RedisResourceConfigurationTemplate;
import kr.co.kwt.devportal.secret.v2.model.template.ResourceConfigurationTemplate;
import kr.co.kwt.devportal.secret.v2.model.template.properties.RedisProperties;
import kr.co.kwt.devportal.secret.v2.repository.ResourceConfigurationTemplateRepository;
import kr.co.kwt.devportal.secret.v2.utility.Flattener;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ResourceConfigurationTemplateRepositoryTest {

    @Autowired
    ResourceConfigurationTemplateRepository resourceConfigurationTemplateRepository;

    @Autowired
    Flattener flattener;

    @Test
    void 저장() {
        RedisProperties redisProperties = RedisProperties
                .builder()
                .host("localhost")
                .port(6379)
                .username("test")
                .password("test")
                .build();

        ResourceConfigurationTemplate<?> resourceConfigurationTemplate = RedisResourceConfigurationTemplate
                .builder()
                .environment(Environment.DEV)
                .properties(redisProperties)
                .build();

        ResourceConfigurationTemplate<?> save =
                resourceConfigurationTemplateRepository.save(resourceConfigurationTemplate);

        RedisProperties properties = (RedisProperties) save.getProperties();
    }

    @Test
    void 조회() {
        List<ResourceConfigurationTemplate<?>> all = resourceConfigurationTemplateRepository
                .findAll();

        for (ResourceConfigurationTemplate<?> resourceConfigurationTemplate : all) {
//            Class<?> propertiesClass = resourceConfigurationTemplate
//                    .getResourceType()
//                    .getPropertiesClass();
//
//            Object properties = propertiesClass.cast(resourceConfigurationTemplate
//                    .getProperties());
            flattener
                    .flattenMap(resourceConfigurationTemplate.getProperties())
                    .entrySet().stream()
                    .forEach(entry -> {
                        System.out.println("entry.getKey() = " + entry.getKey());
                        System.out.println("entry.getValue() = " + entry.getValue());
                    });
        }

        System.out.println("all = " + all);
    }

}