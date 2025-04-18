package kr.co.kwt.devportal.secret.v2.service.command;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.NotNull;
import kr.co.kwt.devportal.secret.v2.model.Environment;
import kr.co.kwt.devportal.secret.v2.model.ResourceConfigurationTemplate;
import kr.co.kwt.devportal.secret.v2.model.ResourceType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.IOException;

import static kr.co.kwt.devportal.secret.v2.service.command.AddResourceConfigurationTemplateCommand.AddResourceConfigurationTemplateCommandDeserializer;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonDeserialize(using = AddResourceConfigurationTemplateCommandDeserializer.class)
public class AddResourceConfigurationTemplateCommand<T> {

    @NotNull
    private ResourceType resourceType;
    @NotNull
    private Environment environment;
    @NotNull
    private T properties;
    @NotNull
    private Boolean supportsAccountProvisioning;

    public ResourceConfigurationTemplate<?> toResourceConfigurationTemplate() {
        return new ResourceConfigurationTemplate<>(
                null,
                environment,
                resourceType,
                properties,
                supportsAccountProvisioning
        );
    }

    public static class AddResourceConfigurationTemplateCommandDeserializer
            extends JsonDeserializer<AddResourceConfigurationTemplateCommand<?>> {

        public static final String RESOURCE_TYPE_FIELD = "resourceType";
        public static final String ENVIRONMENT_FIELD = "environment";
        public static final String PROPERTIES_FIELD = "properties";
        public static final String SUPPORTS_ACCOUNT_PROVISIONING_FIELD = "supportsAccountProvisioning";

        @Override
        public AddResourceConfigurationTemplateCommand<?> deserialize(
                JsonParser jsonParser, DeserializationContext deserializationContext
        ) throws IOException {

            ObjectMapper objectMapper = (ObjectMapper) jsonParser.getCodec();
            TreeNode rootNode = objectMapper.readTree(jsonParser);

            ResourceType resourceType = objectMapper.treeToValue(
                    rootNode.get(RESOURCE_TYPE_FIELD),
                    ResourceType.class);

            Environment environment = objectMapper.treeToValue(
                    rootNode.get(ENVIRONMENT_FIELD),
                    Environment.class);

            Object properties = objectMapper.treeToValue(
                    rootNode.get(PROPERTIES_FIELD),
                    resourceType.getPropertiesClass());

            Boolean supportsAccountProvisioning = objectMapper.treeToValue(
                    rootNode.get(SUPPORTS_ACCOUNT_PROVISIONING_FIELD),
                    Boolean.class);

            return new AddResourceConfigurationTemplateCommand<>(
                    resourceType,
                    environment,
                    properties,
                    supportsAccountProvisioning);
        }
    }
}