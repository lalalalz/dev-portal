package kr.co.kwt.devportal.secret.model.property;

import lombok.Data;

import java.time.Duration;
import java.util.List;

@Data
public class RedisProperties {

    /**
     * Database index used by the connection factory.
     */
    int database = 0;

    /**
     * Connection URL. Overrides host, port, username, and password. Example:
     * redis://user:password@example.com:6379
     */
    String url;

    /**
     * Redis server host.
     */
    String host = "localhost";

    /**
     * Login username of the redis server.
     */
    String username;

    /**
     * Login password of the redis server.
     */
    String password;

    /**
     * Redis server port.
     */
    int port;

    /**
     * Read timeout.
     */
    Duration timeout;

    /**
     * Connection timeout.
     */
    Duration connectTimeout;

    /**
     * Client name to be set on connections with CLIENT SETNAME.
     */
    String clientName;

    private ClientType clientType;

    private Sentinel sentinel;

    private Cluster cluster;

    private final Ssl ssl = new Ssl();

    private final Jedis jedis = new Jedis();

    private final Lettuce lettuce = new Lettuce();

    /**
     * Type of Redis client to use.
     */
    public enum ClientType {

        /**
         * Use the Lettuce redis client.
         */
        LETTUCE,

        /**
         * Use the Jedis redis client.
         */
        JEDIS

    }

    /**
     * Pool properties.
     */
    public static class Pool {

        /**
         * Whether to enable the pool. Enabled automatically if "commons-pool2" is
         * available. With Jedis, pooling is implicitly enabled in sentinel mode and this
         * setting only applies to single node setup.
         */
        private Boolean enabled;

        /**
         * Maximum number of "idle" connections in the pool. Use a negative value to
         * indicate an unlimited number of idle connections.
         */
        private int maxIdle = 8;

        /**
         * Target for the minimum number of idle connections to maintain in the pool. This
         * setting only has an effect if both it and time between eviction runs are
         * positive.
         */
        private int minIdle = 0;

        /**
         * Maximum number of connections that can be allocated by the pool at a given
         * time. Use a negative value for no limit.
         */
        private int maxActive = 8;

        /**
         * Maximum amount of time a connection allocation should block before throwing an
         * exception when the pool is exhausted. Use a negative value to block
         * indefinitely.
         */
        private Duration maxWait = Duration.ofMillis(-1);

        /**
         * Time between runs of the idle object evictor thread. When positive, the idle
         * object evictor thread starts, otherwise no idle object eviction is performed.
         */
        private Duration timeBetweenEvictionRuns;

        public Boolean getEnabled() {
            return this.enabled;
        }

        public void setEnabled(Boolean enabled) {
            this.enabled = enabled;
        }

        public int getMaxIdle() {
            return this.maxIdle;
        }

        public void setMaxIdle(int maxIdle) {
            this.maxIdle = maxIdle;
        }

        public int getMinIdle() {
            return this.minIdle;
        }

        public void setMinIdle(int minIdle) {
            this.minIdle = minIdle;
        }

        public int getMaxActive() {
            return this.maxActive;
        }

        public void setMaxActive(int maxActive) {
            this.maxActive = maxActive;
        }

        public Duration getMaxWait() {
            return this.maxWait;
        }

        public void setMaxWait(Duration maxWait) {
            this.maxWait = maxWait;
        }

        public Duration getTimeBetweenEvictionRuns() {
            return this.timeBetweenEvictionRuns;
        }

        public void setTimeBetweenEvictionRuns(Duration timeBetweenEvictionRuns) {
            this.timeBetweenEvictionRuns = timeBetweenEvictionRuns;
        }

    }

    /**
     * Cluster properties.
     */
    public static class Cluster {

        /**
         * List of "host:port" pairs to bootstrap from. This represents an "initial" list
         * of cluster nodes and is required to have at least one entry.
         */
        private List<String> nodes;

        /**
         * Maximum number of redirects to follow when executing commands across the
         * cluster.
         */
        private Integer maxRedirects;

        public List<String> getNodes() {
            return this.nodes;
        }

        public void setNodes(List<String> nodes) {
            this.nodes = nodes;
        }

        public Integer getMaxRedirects() {
            return this.maxRedirects;
        }

        public void setMaxRedirects(Integer maxRedirects) {
            this.maxRedirects = maxRedirects;
        }

    }

    /**
     * Redis sentinel properties.
     */
    public static class Sentinel {

        /**
         * Name of the Redis server.
         */
        private String master;

        /**
         * List of "host:port" pairs.
         */
        private List<String> nodes;

        /**
         * Login username for authenticating with sentinel(s).
         */
        private String username;

        /**
         * Password for authenticating with sentinel(s).
         */
        private String password;

        public String getMaster() {
            return this.master;
        }

        public void setMaster(String master) {
            this.master = master;
        }

        public List<String> getNodes() {
            return this.nodes;
        }

        public void setNodes(List<String> nodes) {
            this.nodes = nodes;
        }

        public String getUsername() {
            return this.username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return this.password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

    }

    public static class Ssl {

        /**
         * Whether to enable SSL support. Enabled automatically if "bundle" is provided
         * unless specified otherwise.
         */
        private Boolean enabled;

        /**
         * SSL bundle name.
         */
        private String bundle;

        public boolean isEnabled() {
            return (this.enabled != null) ? this.enabled : this.bundle != null;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public String getBundle() {
            return this.bundle;
        }

        public void setBundle(String bundle) {
            this.bundle = bundle;
        }

    }

    /**
     * Jedis client properties.
     */
    public static class Jedis {

        /**
         * Jedis pool configuration.
         */
        private final Pool pool = new Pool();

        public Pool getPool() {
            return this.pool;
        }

    }

    /**
     * Lettuce client properties.
     */
    public static class Lettuce {

        /**
         * Shutdown timeout.
         */
        private Duration shutdownTimeout = Duration.ofMillis(100);

        /**
         * Lettuce pool configuration.
         */
        private final Pool pool = new Pool();

        private final Cluster cluster = new Cluster();

        public Duration getShutdownTimeout() {
            return this.shutdownTimeout;
        }

        public void setShutdownTimeout(Duration shutdownTimeout) {
            this.shutdownTimeout = shutdownTimeout;
        }

        public Pool getPool() {
            return this.pool;
        }

        public Cluster getCluster() {
            return this.cluster;
        }

        public static class Cluster {

            private final Refresh refresh = new Refresh();

            public Refresh getRefresh() {
                return this.refresh;
            }

            public static class Refresh {

                /**
                 * Whether to discover and query all cluster nodes for obtaining the
                 * cluster topology. When set to false, only the initial seed nodes are
                 * used as sources for topology discovery.
                 */
                private boolean dynamicRefreshSources = true;

                /**
                 * Cluster topology refresh period.
                 */
                private Duration period;

                /**
                 * Whether adaptive topology refreshing using all available refresh
                 * triggers should be used.
                 */
                private boolean adaptive;

                public boolean isDynamicRefreshSources() {
                    return this.dynamicRefreshSources;
                }

                public void setDynamicRefreshSources(boolean dynamicRefreshSources) {
                    this.dynamicRefreshSources = dynamicRefreshSources;
                }

                public Duration getPeriod() {
                    return this.period;
                }

                public void setPeriod(Duration period) {
                    this.period = period;
                }

                public boolean isAdaptive() {
                    return this.adaptive;
                }

                public void setAdaptive(boolean adaptive) {
                    this.adaptive = adaptive;
                }

            }

        }

    }
}
