package kr.co.kwt.devportal.secret.model.property;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MongoProperties {

    private String host;
    private String port;
    private String uri;
    private String username;
    private String password;
}
