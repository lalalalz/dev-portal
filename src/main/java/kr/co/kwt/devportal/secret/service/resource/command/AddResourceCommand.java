package kr.co.kwt.devportal.secret.service.resource.command;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.validation.constraints.NotNull;
import kr.co.kwt.devportal.secret.model.resource.ResourceType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "resourceType"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = AddRedisResourceCommand.class, name = "REDIS"),
        @JsonSubTypes.Type(value = AddMySQLResourceCommand.class, name = "MYSQL")
        // 여기에 다른 하위 타입도 필요하다면 추가
})
@AllArgsConstructor
public abstract class AddResourceCommand {

    @NotNull
    private ResourceType resourceType;

}
