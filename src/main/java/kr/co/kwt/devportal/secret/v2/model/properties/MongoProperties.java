package kr.co.kwt.devportal.secret.v2.model.properties;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MongoProperties {

    private String host;
    private String port;
    private String uri;
    private String username;
    private String password;

    @Builder
    public MongoProperties(String host, String port, String uri, String username, String password) {
        this.host = host;
        this.port = port;
        this.uri = uri;
        this.username = username;
        this.password = password;
    }
}
