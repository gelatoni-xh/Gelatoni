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

import javax.sql.DataSource;

/**
 * Ekiden 第二数据源配置
 */
@Configuration
@MapperScan(
        basePackages = "com.csxuhuan.gelatoni.infrastructure.repository.ekiden.mapper",
        sqlSessionFactoryRef = "ekidenSqlSessionFactory"
)
public class EkidenDataSourceConfig {

    @Bean("ekidenDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.ekiden")
    public DataSource ekidenDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean("ekidenSqlSessionFactory")
    public SqlSessionFactory ekidenSqlSessionFactory(
            @Qualifier("ekidenDataSource") DataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean factory = new MybatisSqlSessionFactoryBean();
        factory.setDataSource(dataSource);
        factory.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);
        return factory.getObject();
    }

    @Bean("ekidenSqlSessionTemplate")
    public SqlSessionTemplate ekidenSqlSessionTemplate(
            @Qualifier("ekidenSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
