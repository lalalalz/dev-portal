package kr.co.kwt.devportal.secret.model.resource;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import static lombok.AccessLevel.PROTECTED;

@Getter
@TypeAlias("REDIS")
@Document(collection = "resources")
@NoArgsConstructor(access = PROTECTED)
public class RedisResource extends AbstractResource {

    @Id
    private String id;
    private String host;
    private String port;
    private String username;
    private String password;

    @Builder
    public RedisResource(
            String id,
            String host,
            String port,
            String username,
            String password,
            boolean isPreset
    ) {
        super(ResourceType.REDIS, isPreset);
        this.id = id;
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
    }
}
