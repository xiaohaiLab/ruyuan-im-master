package com.ruyuan2020.im.common.hbase;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author case
 */
@Slf4j
public class HBaseTemplate {

    private final Configuration configuration;

    private volatile Connection connection;

    public HBaseTemplate(Configuration configuration) {
        this.configuration = configuration;
        getConnection();
    }

    @SneakyThrows
    public void save(String tableName, Put put) {
        BufferedMutatorParams mutatorParams = new BufferedMutatorParams(TableName.valueOf(tableName));
        try (BufferedMutator mutator = this.getConnection().getBufferedMutator(mutatorParams.writeBufferSize(3 * 1024 * 1024))) {
            mutator.mutate(put);
            mutator.flush();
        }
    }

    @SneakyThrows
    public void save(String tableName, List<Put> puts) {
        BufferedMutatorParams mutatorParams = new BufferedMutatorParams(TableName.valueOf(tableName));
        try (BufferedMutator mutator = this.getConnection().getBufferedMutator(mutatorParams.writeBufferSize(3 * 1024 * 1024))) {
            mutator.mutate(puts);
            mutator.flush();
        }
    }

    @SneakyThrows
    public <T> T get(String tableName, final String rowName, final String familyName, final String qualifier, final RowMapper<T> mapper) {
        try (Table table = this.getConnection().getTable(TableName.valueOf(tableName))) {
            Get get = new Get(Bytes.toBytes(rowName));
            if (StringUtils.isNotBlank(familyName)) {
                byte[] family = Bytes.toBytes(familyName);
                if (StringUtils.isNotBlank(qualifier)) {
                    get.addColumn(family, Bytes.toBytes(qualifier));
                } else {
                    get.addFamily(family);
                }
            }
            Result result = table.get(get);
            return mapper.map(result);
        }
    }

    @SneakyThrows
    public <T> List<T> list(String tableName, Scan scan, final RowMapper<T> mapper) {
        try (Table table = this.getConnection().getTable(TableName.valueOf(tableName))) {
            int caching = scan.getCaching();
            if (caching == 1) {
                scan.setCaching(1000);
            }
            try (ResultScanner scanner = table.getScanner(scan)) {
                List<T> rs = new ArrayList<>();
                for (Result result : scanner) {
                    rs.add(mapper.map(result));
                }
                return rs;
            }
        }
    }


    public Connection getConnection() {
        if (null == this.connection) {
            synchronized (this) {
                if (null == this.connection) {
                    try {
                        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(200, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue<>());
                        // init pool
                        poolExecutor.prestartCoreThread();
                        this.connection = ConnectionFactory.createConnection(configuration, poolExecutor);
                    } catch (Exception e) {
                        log.error("Hbase连接创建失败");
                    }
                }
            }
        }
        return this.connection;
    }

    @SneakyThrows
    public boolean exists(String tableName, Get get) {
        try (Table table = getConnection().getTable(TableName.valueOf(tableName))) {
            return table.exists(get);
        }
    }
}
