package com.stone.config;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

public class MybatisConfig {

    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean(DataSource dataSource) {
        //spring整合mybatis依赖中提供了SqlSessionFactoryBean对象，可以让我们快速地创建SqlSessionFactory对象。
        //通过查看SqlSessionFactoryBean的源码，发现该类实现了FactoryBean接口，且泛型为SqlSessionFactory。
        SqlSessionFactoryBean ssfBean = new SqlSessionFactoryBean();
        ssfBean.setTypeAliasesPackage("com.stone.domain");
        ssfBean.setDataSource(dataSource);
        return ssfBean;
    }

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        //MapperScannerConfigurer是mybatis-spring提供的，用于扫描指定包下的Mapper接口，
        //并自动创建MapperFactoryBean对象，将Mapper接口注入到Spring容器中。
        MapperScannerConfigurer msc = new MapperScannerConfigurer();
        msc.setBasePackage("com.stone.dao");
        return msc;
    }
}
