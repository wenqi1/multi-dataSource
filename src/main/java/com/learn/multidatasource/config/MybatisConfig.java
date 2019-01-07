package com.learn.multidatasource.config;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.learn.multidatasource.enums.DatabaseType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import tk.mybatis.spring.annotation.MapperScan;
import tk.mybatis.spring.mapper.MapperScannerConfigurer;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
@MapperScan(basePackages = "com.learn.multidatasource.mapper")
public class MybatisConfig implements EnvironmentAware {
    private Environment environment;

    @Override
    public void setEnvironment(final Environment environment) {
        this.environment = environment;
    }

    /**
     * 创建数据源
     */
    @Bean
    public DataSource erpDataSource() throws Exception {
        Properties props = new Properties();
        props.put("driverClassName", environment.getProperty("jdbc1.driverClassName"));
        props.put("url", environment.getProperty("jdbc1.url"));
        props.put("username", environment.getProperty("jdbc1.username"));
        props.put("password", environment.getProperty("jdbc1.password"));
        return DruidDataSourceFactory.createDataSource(props);
    }

    @Bean
    public DataSource learnDataSource() throws Exception {
        Properties props = new Properties();
        props.put("driverClassName", environment.getProperty("jdbc2.driverClassName"));
        props.put("url", environment.getProperty("jdbc2.url"));
        props.put("username", environment.getProperty("jdbc2.username"));
        props.put("password", environment.getProperty("jdbc2.password"));
        return DruidDataSourceFactory.createDataSource(props);
    }

    /**
     * @Primary 该注解表示在同一个接口有多个实现类可以注入的时候，默认选择哪一个，而不是让@autowire注解报错
     * @Qualifier 根据名称进行注入
     */
    @Bean
    @Primary
    public DynamicDataSource dataSource(@Qualifier("erpDataSource") DataSource erpDataSource,
                                        @Qualifier("learnDataSource") DataSource learnDataSource) {
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DatabaseType.ERP, erpDataSource);
        targetDataSources.put(DatabaseType.LEARN, learnDataSource);

        DynamicDataSource dataSource = new DynamicDataSource();
        // 该方法是AbstractRoutingDataSource的方法
        dataSource.setTargetDataSources(targetDataSources);
        // 默认的datasource设置为erpDataSource
        dataSource.setDefaultTargetDataSource(erpDataSource);

        return dataSource;
    }

    /**
     * 配置通用mapper
     * @return
     */
    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer scannerConfigurer = new MapperScannerConfigurer();
        scannerConfigurer.setBasePackage("com.learn.multidatasource.mapper");
        Properties props = new Properties();
        props.setProperty("mappers", environment.getProperty("mapper.mappers"));
        props.setProperty("IDENTITY", environment.getProperty("mapper.identity"));
        props.setProperty("notEmpty", environment.getProperty("mapper.not-empty"));
        props.setProperty("enumAsSimpleType",environment.getProperty("mapper.enum-as-simple-type"));
        scannerConfigurer.setProperties(props);
        return scannerConfigurer;
    }

    /**
     * 根据数据源创建SqlSessionFactory
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory(DynamicDataSource ds) throws Exception {
        SqlSessionFactoryBean fb = new SqlSessionFactoryBean();
        // 指定数据源
        fb.setDataSource(ds);
        //指定实体类路径
        fb.setTypeAliasesPackage(environment.getProperty("mybatis.type-aliases-package"));
        //指定mapper映射文件路径
        //fb.setMapperLocations(
                //new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml"));
        return fb.getObject();
    }

    /**
     * 配置事务管理器
     */
    @Bean
    public DataSourceTransactionManager transactionManager(DynamicDataSource dataSource) throws Exception {
        return new DataSourceTransactionManager(dataSource);
    }

}
