package kr.co.kwt.devportal.secret.v2.model.template.properties;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.sql.DataSource;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DataSourceProperties {

    private ClassLoader classLoader;

    /**
     * Whether to generate a random datasource name.
     */
//    private boolean generateUniqueName = true;

    /**
     * Datasource name to use if "generate-unique-name" is false. Defaults to "testdb"
     * when using an embedded database, otherwise null.
     */
    private String name;

    /**
     * Fully qualified name of the DataSource implementation to use. By default, a
     * connection pool implementation is auto-detected from the classpath.
     */
    private Class<? extends DataSource> type;

    /**
     * Fully qualified name of the JDBC driver. Auto-detected based on the URL by default.
     */
    private String driverClassName;

    /**
     * JDBC URL of the database.
     */
    private String url;

}
