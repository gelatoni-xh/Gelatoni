package com.csxuhuan.gelatoni.infrastructure.config;

import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${DB_DRIVER_CLASS_NAME:com.mysql.cj.jdbc.Driver}")
    private String driverClassName;

    @Value("${DB_URL:jdbc:mysql://localhost:3306/gelatoni}")
    private String url;

    @Value("${DB_USER}")
    private String username;

    @Value("${DB_PASSWORD}")
    private String password;

    @Primary
    @Bean("gelatoniDataSource")
    public DataSource gelatoniDataSource() {
        return DataSourceBuilder.create()
                .driverClassName(driverClassName)
                .url(url)
                .username(username)
                .password(password)
                .build();
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
