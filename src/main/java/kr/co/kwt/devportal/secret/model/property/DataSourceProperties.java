package kr.co.kwt.devportal.secret.model.property;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DataSourceProperties {
//
//    ClassLoader classLoader;
//
//    /**
//     * Whether to generate a random datasource name.
//     */
//    boolean generateUniqueName = true;
//
//    /**
//     * Datasource name to use if "generate-unique-name" is false. Defaults to "testdb"
//     * when using an embedded database, otherwise null.
//     */
//    String name;

//    /**
//     * Fully qualified name of the DataSource implementation to use. By default, a
//     * connection pool implementation is auto-detected from the classpath.
//     */
//    Class<? extends DataSource> type;

//    /**
//     * Fully qualified name of the JDBC driver. Auto-detected based on the URL by default.
//     */
//    String driverClassName;

    /**
     * JDBC URL of the database.
     */
    String url;

    /**
     * Login username of the database.
     */
    String username;

    /**
     * Login password of the database.
     */
    String password;

//    /**
//     * JNDI location of the datasource. Class, url, username and password are ignored when
//     * set.
//     */
//    String jndiName;

}
