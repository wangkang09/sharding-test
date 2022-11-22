//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.apache.shardingsphere.driver.jdbc.core.datasource;

import com.google.common.base.Preconditions;
import lombok.Generated;
import org.apache.shardingsphere.driver.jdbc.core.connection.ShardingSphereConnection;
import org.apache.shardingsphere.driver.jdbc.unsupported.AbstractUnsupportedOperationDataSource;
import org.apache.shardingsphere.infra.config.RuleConfiguration;
import org.apache.shardingsphere.infra.context.schema.SchemaContexts;
import org.apache.shardingsphere.infra.context.schema.SchemaContextsBuilder;
import org.apache.shardingsphere.infra.database.type.DatabaseType;
import org.apache.shardingsphere.infra.database.type.DatabaseTypeRegistry;
import org.apache.shardingsphere.transaction.ShardingTransactionManagerEngine;
import org.apache.shardingsphere.transaction.context.TransactionContexts;
import org.apache.shardingsphere.transaction.context.impl.StandardTransactionContexts;
import org.apache.shardingsphere.transaction.core.TransactionTypeHolder;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

public class ShardingSphereDataSource extends AbstractUnsupportedOperationDataSource implements AutoCloseable {
    private final SchemaContexts schemaContexts;
    private final TransactionContexts transactionContexts;

    public ShardingSphereDataSource(Map<String, DataSource> dataSourceMap, Collection<RuleConfiguration> configurations, Properties props) throws SQLException {
        DatabaseType databaseType = this.createDatabaseType(dataSourceMap);
        this.schemaContexts = (new SchemaContextsBuilder(databaseType, Collections.singletonMap("logic_db", dataSourceMap), Collections.singletonMap("logic_db", configurations), props)).build();
        this.transactionContexts = this.createTransactionContexts(databaseType, dataSourceMap);
    }

    private DatabaseType createDatabaseType(Map<String, DataSource> dataSourceMap) throws SQLException {
        DatabaseType result = null;

        DatabaseType databaseType;
        for (Iterator var3 = dataSourceMap.values().iterator(); var3.hasNext(); result = databaseType) {
            DataSource each = (DataSource) var3.next();
            databaseType = this.createDatabaseType(each);
            Preconditions.checkState(null == result || result == databaseType, String.format("Database type inconsistent with '%s' and '%s'", result, databaseType));
        }

        return result;
    }

    private DatabaseType createDatabaseType(DataSource dataSource) throws SQLException {
        if (dataSource instanceof ShardingSphereDataSource) {
            return ((ShardingSphereDataSource) dataSource).schemaContexts.getDatabaseType();
        } else {
            Connection connection = dataSource.getConnection();
            Throwable var3 = null;

            DatabaseType var4;
            try {
                var4 = DatabaseTypeRegistry.getDatabaseTypeByURL(connection.getMetaData().getURL());
            } catch (Throwable var13) {
                var3 = var13;
                throw var13;
            } finally {
                if (connection != null) {
                    if (var3 != null) {
                        try {
                            connection.close();
                        } catch (Throwable var12) {
                            var3.addSuppressed(var12);
                        }
                    } else {
                        connection.close();
                    }
                }

            }

            return var4;
        }
    }

    private TransactionContexts createTransactionContexts(DatabaseType databaseType, Map<String, DataSource> dataSourceMap) {
        ShardingTransactionManagerEngine engine = new ShardingTransactionManagerEngine();
        engine.init(databaseType, dataSourceMap);
        return new StandardTransactionContexts(Collections.singletonMap("logic_db", engine));
    }

    public ShardingSphereConnection getConnection() {
        return new ShardingSphereConnection(this.getDataSourceMap(), this.schemaContexts, this.transactionContexts, TransactionTypeHolder.get());
    }

    public ShardingSphereConnection getConnection(String username, String password) {
        return this.getConnection();
    }

    public Map<String, DataSource> getDataSourceMap() {
        return this.schemaContexts.getDefaultSchema().getDataSources();
    }

    public void close() throws Exception {
        this.close((Collection) this.getDataSourceMap().keySet());
    }

    public void close(Collection<String> dataSourceNames) throws Exception {
        Iterator var2 = dataSourceNames.iterator();

        while (var2.hasNext()) {
            String each = (String) var2.next();
            this.close((DataSource) this.getDataSourceMap().get(each));
        }

        this.schemaContexts.close();
    }

    private void close(DataSource dataSource) throws Exception {
        if (dataSource instanceof AutoCloseable) {
            ((AutoCloseable) dataSource).close();
        }

    }

    @Generated
    public ShardingSphereDataSource(SchemaContexts schemaContexts, TransactionContexts transactionContexts) {
        this.schemaContexts = schemaContexts;
        this.transactionContexts = transactionContexts;
    }

    @Generated
    public SchemaContexts getSchemaContexts() {
        return this.schemaContexts;
    }

    @Generated
    public TransactionContexts getTransactionContexts() {
        return this.transactionContexts;
    }
}
