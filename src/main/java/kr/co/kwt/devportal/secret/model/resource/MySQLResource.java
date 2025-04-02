package kr.co.kwt.devportal.secret.model.resource;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.lang.Nullable;

import static kr.co.kwt.devportal.secret.model.resource.provisioner.ResourceAccountProvisioner.UsernameAndPassword;
import static lombok.AccessLevel.PROTECTED;

@Getter
@TypeAlias("MYSQL")
@Document(collection = "resources")
@NoArgsConstructor(access = PROTECTED)
public class MySQLResource extends AbstractResource implements CloneableResource {

    @Id
    private String id;
    private String host;
    private String port;
    @Nullable
    private String username;
    @Nullable
    private String password;

    @Builder
    protected MySQLResource(
            String id,
            String host,
            String port,
            @Nullable String username,
            @Nullable String password,
            boolean isPreset
    ) {
        super(ResourceType.MYSQL, isPreset);
        this.id = id;
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
    }

    @Override
    public Resource cloneWithAccount(UsernameAndPassword usernameAndPassword) {
        return MySQLResource
                .builder()
                .id(null)
                .host(host)
                .port(port)
                .username(usernameAndPassword.getUsername())
                .password(usernameAndPassword.getPassword())
                .build();
    }
}
