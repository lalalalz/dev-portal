package kr.co.kwt.devportal.secret.v2.model.properties;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DataSourceProperties {

    private String username;
    private String password;
    private String url;
    private String driverClassName;
}
