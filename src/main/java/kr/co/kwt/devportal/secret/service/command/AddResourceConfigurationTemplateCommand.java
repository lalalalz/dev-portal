package kr.co.kwt.devportal.secret.service.command;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.validation.constraints.NotNull;
import kr.co.kwt.devportal.secret.model.ResourceType;
import lombok.Getter;

@Getter
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "resourceType",
        visible = true  // resourceType에 값을 사용 후에 바인딩 하도록 설정
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = AddMysqlResourceConfigurationCommand.class, name = "MYSQL"),
        @JsonSubTypes.Type(value = AddRedisResourceConfigurationCommand.class, name = "REDIS")
})
public abstract class AddResourceConfigurationTemplateCommand<T> {

    @NotNull
    protected ResourceType resourceType;
    @NotNull
    protected T resourceProperties;
}
