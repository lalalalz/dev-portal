package kr.co.kwt.devportal.secret.v2.model.properties;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class KafkaProperties {

    private String bootstrapServers;
    private String username;
    private String password;

    @Builder
    public KafkaProperties(String bootstrapServers, String username, String password) {
        this.bootstrapServers = bootstrapServers;
        this.username = username;
        this.password = password;
    }
}
