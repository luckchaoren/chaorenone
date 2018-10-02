//package com.chaoren.config;//package com.mongo.demo.config;
//
//import com.alibaba.druid.pool.DruidDataSource;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.core.env.Environment;
//import org.springframework.jdbc.core.JdbcTemplate;
//
//import javax.sql.DataSource;
//import java.sql.SQLException;
//import java.util.Date;
//
///**
// * Created by lichao on 2018/9/25.
// * 数据源初始化
// */
//@Configuration
//public class DatasourceConfiguration {
//
//    private static Logger logger = LoggerFactory.getLogger(DatasourceConfiguration.class);
//
//    @Autowired
//    private Environment env;
//
//    @Bean
//    @Primary
//    public JdbcTemplate JdbcTemplate(DataSource dataSource) {
//        logger.info(new Date()+"<--------->调用数据源产生 JdbcTemplate 一次 <----------->");
//        return new JdbcTemplate(dataSource);
//    }
//
//    @Bean
//    @Primary
//    public DataSource dataSource() {
//        DruidDataSource dataSource = new DruidDataSource();
//
//        dataSource.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
//        dataSource.setUrl(env.getProperty("spring.datasource.url"));
//        dataSource.setUsername(env.getProperty("spring.datasource.username"));
//        dataSource.setPassword(env.getProperty("spring.datasource.password"));
//        dataSource.setInitialSize(Integer.valueOf(env.getProperty("spring.datasource.initialSize")));
//        dataSource.setMinIdle(Integer.valueOf(env.getProperty("spring.datasource.minIdle")));
//        dataSource.setMaxActive(Integer.valueOf(env.getProperty("spring.datasource.maxActive")));
//        dataSource.setMaxWait(Long.valueOf(env.getProperty("spring.datasource.maxWait")));
//        dataSource.setTimeBetweenEvictionRunsMillis(Long.valueOf(env.getProperty("spring.datasource.timeBetweenEvictionRunsMillis")));
//        dataSource.setMinEvictableIdleTimeMillis(Long.valueOf(env.getProperty("spring.datasource.minEvictableIdleTimeMillis")));
//        dataSource.setValidationQuery(env.getProperty("spring.datasource.validationQuery"));
//        dataSource.setTestWhileIdle("true".equals(env.getProperty("spring.datasource.testWhileIdle")));
//        dataSource.setTestOnBorrow("ture".equals(env.getProperty("spring.datasource.testOnBorrow")));
//        dataSource.setTestOnReturn("true".equals(env.getProperty("spring.datasource.testOnReturn")));
//
//        try {
//            dataSource.setFilters(env.getProperty("spring.datasource.filters"));
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        dataSource.setPoolPreparedStatements("true".equals(env.getProperty("spring.datasource.poolPreparedStatements")));
//        dataSource.setMaxPoolPreparedStatementPerConnectionSize(Integer.valueOf(env.getProperty("spring.datasource.maxPoolPreparedStatementPerConnectionSize")));
//
//        return dataSource;
//    }
//
//}
