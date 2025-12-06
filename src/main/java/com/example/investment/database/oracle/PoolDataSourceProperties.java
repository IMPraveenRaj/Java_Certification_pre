package com.example.investment.database.oracle;

import oracle.ucp.jdbc.PoolDataSource;
import oracle.ucp.jdbc.PoolDataSourceFactory;

import java.sql.SQLException;

/**
 * Wrapper for Oracle UCP PoolDataSource configuration.
 * Values are typically populated via @ConfigurationProperties("spring.datasource").
 */
public class PoolDataSourceProperties {

    // --- basic connection ---
    private String url;
    private String username;
    private String password;

    // --- pool identity & factory ---
    private String connectionPoolName;
    private String connectionFactoryClassName;
    private boolean fastConnectionFailoverEnabled;

    // --- pool sizes ---
    private Integer initialPoolSize;
    private Integer minPoolSize;
    private Integer maxPoolSize;

    // --- program / encryption ---
    private String program;
    private String encryptionLevel;
    private String encryptionTypes;

    // --- reuse / lifetime limits (seconds) ---
    private Integer maxConnectionReuseTime;
    private Integer maxConnectionReuseCount;

    // --- timeouts (seconds) ---
    private Integer abandonedConnectionTimeout;
    private Integer connectionWaitTimeout;
    private Integer inactiveConnectionTimeout;
    private Integer timeoutCheckInterval;
    private Integer queryTimeout;
    private Integer timeToLiveConnectionTimeout;

    // --- validation ---
    private String sqlForValidateConnection;
    private boolean validateConnectionOnBorrow;

    /**
     * Build and configure an Oracle UCP PoolDataSource from these properties.
     */
    public PoolDataSource build() throws SQLException {
        PoolDataSource dataSource = PoolDataSourceFactory.getPoolDataSource();

        // required basics
        dataSource.setURL(this.url);
        dataSource.setUser(this.username);
        dataSource.setPassword(this.password);
        dataSource.setConnectionFactoryClassName(this.connectionFactoryClassName);
        dataSource.setFastConnectionFailoverEnabled(this.fastConnectionFailoverEnabled);
        dataSource.setValidateConnectionOnBorrow(this.validateConnectionOnBorrow);

        if (this.initialPoolSize != null) {
            dataSource.setInitialPoolSize(this.initialPoolSize);
        }
        if (this.connectionPoolName != null) {
            dataSource.setConnectionPoolName(this.connectionPoolName);
        }
        if (this.minPoolSize != null) {
            dataSource.setMinPoolSize(this.minPoolSize);
        }
        if (this.maxPoolSize != null) {
            dataSource.setMaxPoolSize(this.maxPoolSize);
        }

        if (this.program != null) {
            dataSource.setConnectionProperty("v$session.program", this.program);
        }
        if (this.encryptionLevel != null) {
            dataSource.setConnectionProperty("oracle.net.encryption_client", this.encryptionLevel);
        }
        if (this.encryptionTypes != null) {
            dataSource.setConnectionProperty("oracle.net.encryption_types_client", this.encryptionTypes);
        }

        if (this.maxConnectionReuseTime != null) {
            dataSource.setMaxConnectionReuseTime(this.maxConnectionReuseTime);
        }
        if (this.maxConnectionReuseCount != null) {
            dataSource.setMaxConnectionReuseCount(this.maxConnectionReuseCount);
        }

        if (this.abandonedConnectionTimeout != null) {
            dataSource.setAbandonedConnectionTimeout(this.abandonedConnectionTimeout);
        }
        if (this.connectionWaitTimeout != null) {
            dataSource.setConnectionWaitTimeout(this.connectionWaitTimeout);
        }
        if (this.inactiveConnectionTimeout != null) {
            dataSource.setInactiveConnectionTimeout(this.inactiveConnectionTimeout);
        }
        if (this.timeoutCheckInterval != null) {
            dataSource.setTimeoutCheckInterval(this.timeoutCheckInterval);
        }

        if (this.sqlForValidateConnection != null) {
            dataSource.setSQLForValidateConnection(this.sqlForValidateConnection);
        }
        if (this.queryTimeout != null) {
            dataSource.setQueryTimeout(this.queryTimeout);
        }
        if (this.timeToLiveConnectionTimeout != null) {
            dataSource.setTimeToLiveConnectionTimeout(this.timeToLiveConnectionTimeout);
        }

        return dataSource;
    }

    // --- getters & setters (you can replace with Lombok @Data if you like) ---

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConnectionPoolName() {
        return connectionPoolName;
    }

    public void setConnectionPoolName(String connectionPoolName) {
        this.connectionPoolName = connectionPoolName;
    }

    public String getConnectionFactoryClassName() {
        return connectionFactoryClassName;
    }

    public void setConnectionFactoryClassName(String connectionFactoryClassName) {
        this.connectionFactoryClassName = connectionFactoryClassName;
    }

    public boolean isFastConnectionFailoverEnabled() {
        return fastConnectionFailoverEnabled;
    }

    public void setFastConnectionFailoverEnabled(boolean fastConnectionFailoverEnabled) {
        this.fastConnectionFailoverEnabled = fastConnectionFailoverEnabled;
    }

    public Integer getInitialPoolSize() {
        return initialPoolSize;
    }

    public void setInitialPoolSize(Integer initialPoolSize) {
        this.initialPoolSize = initialPoolSize;
    }

    public Integer getMinPoolSize() {
        return minPoolSize;
    }

    public void setMinPoolSize(Integer minPoolSize) {
        this.minPoolSize = minPoolSize;
    }

    public Integer getMaxPoolSize() {
        return maxPoolSize;
    }

    public void setMaxPoolSize(Integer maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public String getEncryptionLevel() {
        return encryptionLevel;
    }

    public void setEncryptionLevel(String encryptionLevel) {
        this.encryptionLevel = encryptionLevel;
    }

    public String getEncryptionTypes() {
        return encryptionTypes;
    }

    public void setEncryptionTypes(String encryptionTypes) {
        this.encryptionTypes = encryptionTypes;
    }

    public Integer getMaxConnectionReuseTime() {
        return maxConnectionReuseTime;
    }

    public void setMaxConnectionReuseTime(Integer maxConnectionReuseTime) {
        this.maxConnectionReuseTime = maxConnectionReuseTime;
    }

    public Integer getMaxConnectionReuseCount() {
        return maxConnectionReuseCount;
    }

    public void setMaxConnectionReuseCount(Integer maxConnectionReuseCount) {
        this.maxConnectionReuseCount = maxConnectionReuseCount;
    }

    public Integer getAbandonedConnectionTimeout() {
        return abandonedConnectionTimeout;
    }

    public void setAbandonedConnectionTimeout(Integer abandonedConnectionTimeout) {
        this.abandonedConnectionTimeout = abandonedConnectionTimeout;
    }

    public Integer getConnectionWaitTimeout() {
        return connectionWaitTimeout;
    }

    public void setConnectionWaitTimeout(Integer connectionWaitTimeout) {
        this.connectionWaitTimeout = connectionWaitTimeout;
    }

    public Integer getInactiveConnectionTimeout() {
        return inactiveConnectionTimeout;
    }

    public void setInactiveConnectionTimeout(Integer inactiveConnectionTimeout) {
        this.inactiveConnectionTimeout = inactiveConnectionTimeout;
    }

    public Integer getTimeoutCheckInterval() {
        return timeoutCheckInterval;
    }

    public void setTimeoutCheckInterval(Integer timeoutCheckInterval) {
        this.timeoutCheckInterval = timeoutCheckInterval;
    }

    public String getSqlForValidateConnection() {
        return sqlForValidateConnection;
    }

    public void setSqlForValidateConnection(String sqlForValidateConnection) {
        this.sqlForValidateConnection = sqlForValidateConnection;
    }

    public boolean isValidateConnectionOnBorrow() {
        return validateConnectionOnBorrow;
    }

    public void setValidateConnectionOnBorrow(boolean validateConnectionOnBorrow) {
        this.validateConnectionOnBorrow = validateConnectionOnBorrow;
    }

    public Integer getQueryTimeout() {
        return queryTimeout;
    }

    public void setQueryTimeout(Integer queryTimeout) {
        this.queryTimeout = queryTimeout;
    }

    public Integer getTimeToLiveConnectionTimeout() {
        return timeToLiveConnectionTimeout;
    }

    public void setTimeToLiveConnectionTimeout(Integer timeToLiveConnectionTimeout) {
        this.timeToLiveConnectionTimeout = timeToLiveConnectionTimeout;
    }
}
