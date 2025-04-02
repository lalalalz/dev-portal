package kr.co.kwt.devportal.secret.service.resource.command;

import com.fasterxml.jackson.annotation.JsonTypeName;
import kr.co.kwt.devportal.secret.model.resource.ResourceType;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@JsonTypeName("MYSQL")
@EqualsAndHashCode(callSuper = true)
public class AddMySQLResourceCommand extends AddResourceCommand {

    String host;
    String port;

    public AddMySQLResourceCommand(String host, String port) {
        super(ResourceType.MYSQL);
        this.host = host;
        this.port = port;
    }
}
