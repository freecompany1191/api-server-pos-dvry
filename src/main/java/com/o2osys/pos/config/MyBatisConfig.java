/**
 *
 */
package com.o2osys.pos.config;

import java.io.IOException;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.o2osys.pos.config.dataSource.Master;
import com.o2osys.pos.config.dataSource.Slave;

/**
 * @author stunstun(minhyuck.jung@nhnent.com)
 *
 */
@Configuration
public abstract class MyBatisConfig {

    public static final String BASE_PACKAGE = "com.o2osys.pos.mapper";
    public static final String TYPE_ALIASES_PACKAGE = "com.o2osys.pos.entity";
    //    public static final String CONFIG_LOCATION_PATH = "classpath:mybatis-config.xml";
    public static final String MAPPER_LOCATIONS_PATH = "classpath:mapper/**/*.xml";

    protected void configureSqlSessionFactory(SqlSessionFactoryBean sessionFactoryBean, DataSource dataSource)
            throws IOException {
        PathMatchingResourcePatternResolver pathResolver = new PathMatchingResourcePatternResolver();
        sessionFactoryBean.setDataSource(dataSource);
        sessionFactoryBean.setTypeAliasesPackage(TYPE_ALIASES_PACKAGE);
        //        sessionFactoryBean.setConfigLocation(pathResolver.getResource(CONFIG_LOCATION_PATH));
        sessionFactoryBean.setMapperLocations(pathResolver.getResources(MAPPER_LOCATIONS_PATH));
    }
}

@Configuration
@MapperScan(basePackages = MyBatisConfig.BASE_PACKAGE, annotationClass = Master.class, sqlSessionFactoryRef = "masterSqlSessionFactory")
class MasterMyBatisConfig extends MyBatisConfig {

    private final Logger log = LoggerFactory.getLogger(MasterMyBatisConfig.class);

    @Bean
    @Primary
    public SqlSessionFactory masterSqlSessionFactory(@Qualifier("masterDataSource") DataSource masterDataSource)
            throws Exception {

        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        configureSqlSessionFactory(sessionFactoryBean, masterDataSource);
        return sessionFactoryBean.getObject();
    }

    @Bean("masterSqlSessionTemplate")
    public SqlSessionTemplate masterSqlSessionTemplate(SqlSessionFactory masterSqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(masterSqlSessionFactory);
    }
}

@Configuration
@MapperScan(basePackages = MyBatisConfig.BASE_PACKAGE, annotationClass = Slave.class, sqlSessionFactoryRef = "slaveSqlSessionFactory")
class SlaveMyBatisConfig extends MyBatisConfig {

    @Bean
    public SqlSessionFactory slaveSqlSessionFactory(@Qualifier("slaveDataSource") DataSource slaveDataSource)
            throws Exception {
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        configureSqlSessionFactory(sessionFactoryBean, slaveDataSource);
        return sessionFactoryBean.getObject();
    }

    @Bean("slaveSqlSessionTemplate")
    public SqlSessionTemplate slaveSqlSessionTemplate(SqlSessionFactory slaveSqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(slaveSqlSessionFactory);
    }
}


