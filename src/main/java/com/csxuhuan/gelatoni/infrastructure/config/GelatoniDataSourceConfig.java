package com.csxuhuan.gelatoni.infrastructure.config;

import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * Gelatoni 主数据源配置
 */
@Configuration
@MapperScan(
        basePackages = "com.csxuhuan.gelatoni.infrastructure.repository.mapper",
        sqlSessionFactoryRef = "gelatoniSqlSessionFactory"
)
public class GelatoniDataSourceConfig {

    @Primary
    @Bean("gelatoniDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.gelatoni")
    public DataSource gelatoniDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean("gelatoniSqlSessionFactory")
    public SqlSessionFactory gelatoniSqlSessionFactory(
            @Qualifier("gelatoniDataSource") DataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean factory = new MybatisSqlSessionFactoryBean();
        factory.setDataSource(dataSource);
        factory.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);
        return factory.getObject();
    }

    @Bean("gelatoniSqlSessionTemplate")
    public SqlSessionTemplate gelatoniSqlSessionTemplate(
            @Qualifier("gelatoniSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
