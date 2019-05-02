package com.lucy.datasource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages="com.lucy.mapper.learn", sqlSessionTemplateRef="learnSqlSessionTemplate")
public class LearnDataSourceConfig {

    @Bean("learnDataSource")
    @ConfigurationProperties("spring.datasource.learn")
    public DataSource learnDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean("learnSqlSessionFactory")
    public SqlSessionFactory learnSqlSessionFactory(@Qualifier("learnDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionfactoryBean = new SqlSessionFactoryBean();
        sqlSessionfactoryBean.setDataSource(dataSource);

        return sqlSessionfactoryBean.getObject();
    }

    @Bean("learnSqlSessionTemplate")
    public SqlSessionTemplate learnSqlSessionTemplate(@Qualifier("learnSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean("learnDataSourceTransactionManager")
    public DataSourceTransactionManager learnDataSourceTransactionManager(@Qualifier("learnDataSource") DataSource dataSource) {
        return  new DataSourceTransactionManager(dataSource);
    }

}
