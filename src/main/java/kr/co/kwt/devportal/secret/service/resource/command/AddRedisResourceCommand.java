package kr.co.kwt.devportal.secret.service.resource.command;

import com.fasterxml.jackson.annotation.JsonTypeName;
import kr.co.kwt.devportal.secret.model.resource.ResourceType;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@JsonTypeName("REDIS")
@EqualsAndHashCode(callSuper = true)
public class AddRedisResourceCommand extends AddResourceCommand {

    String username;
    String password;
    String host;
    String port;

    public AddRedisResourceCommand(String username, String password, String host, String port) {
        super(ResourceType.REDIS);
        this.username = username;
        this.password = password;
        this.host = host;
        this.port = port;
    }
}
