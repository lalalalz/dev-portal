package kr.co.kwt.devportal.secret.model.property;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class KafkaProperties {

    private String bootstrapServers;
    private String username;
    private String password;
}
