package kr.co.kwt.devportal.secret;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "kr.co.kwt.devportal.secret.v2")
@EnableConfigurationProperties
public class DevPortalApplication {

    public static void main(String[] args) {
        SpringApplication.run(DevPortalApplication.class, args);
    }

}
