package kr.co.kwt.devportal.secret.v2.configuration;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.kwt.devportal.secret.v2.model.Environment;
import kr.co.kwt.devportal.secret.v2.model.ResourceConfigurationTemplate;
import kr.co.kwt.devportal.secret.v2.model.ResourceType;
import org.bson.Document;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@ReadingConverter
public class MongoReadingConverter implements Converter<Document, ResourceConfigurationTemplate<?>> {

    public static final String RESOURCE_TYPE = "resourceType";
    public static final String PROPERTIES = "properties";
    public static final String ID = "_id";
    public static final String ENVIRONMENT = "environment";
    public static final String SUPPORTS_ACCOUNT_PROVISIONING = "supportsAccountProvisioning";

    @Override
    public ResourceConfigurationTemplate<?> convert(Document source) {
        if (!source.containsKey(RESOURCE_TYPE)) {
            throw new IllegalArgumentException("resourceType is not specified");
        }

        try {
            String id = source.getObjectId(ID).toString();
            Environment environment = Environment.valueOf(source.get(ENVIRONMENT, String.class));
            Boolean supportsAccountProvisioning = source.get(SUPPORTS_ACCOUNT_PROVISIONING, Boolean.class);
            String resourceTypeValue = (String) source.get(RESOURCE_TYPE);
            ResourceType resourceType = ResourceType.valueOf(resourceTypeValue);

            // ObjectMapper 설정
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

            // properties 변환
            Object properties = source.get(PROPERTIES);
            if (properties instanceof Document document) {
                String json = document.toJson();
                properties = mapper.readValue(json, resourceType.getPropertiesClass());
            }

            return ResourceConfigurationTemplate
                    .builder()
                    .id(id)
                    .environment(environment)
                    .resourceType(resourceType)
                    .properties(properties)
                    .supportsAccountProvisioning(supportsAccountProvisioning)
                    .build();
        }
        catch (Exception e) {
            // 디버깅
            System.out.println("Conversion error: " + e.getMessage());
            throw new RuntimeException("Failed to convert Document to Template: " + e.getMessage(), e);
        }
    }
}
