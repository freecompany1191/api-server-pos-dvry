package com.o2osys.pos.config.dataSource;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.descriptor.web.ContextResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jndi.JndiTemplate;
import org.springframework.transaction.PlatformTransactionManager;

import com.o2osys.pos.common.constants.Define;

@Configuration
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
public class JndiConfiguration {

    @Autowired
    private JndiProperties jndiProdProperties;

    /**
     * 로컬에서 테스트시 Embedded 톰켓에 jndi 리소스를 셋팅해서 테스트가 가능하도록 함
     * @Method Name : tomcatFactory
     * @return
     */
    @Bean
    @Profile("local")
    public TomcatEmbeddedServletContainerFactory tomcatFactory() {
        return new TomcatEmbeddedServletContainerFactory() {

            @Override
            protected TomcatEmbeddedServletContainer getTomcatEmbeddedServletContainer(
                    Tomcat tomcat) {
                tomcat.enableNaming();
                return super.getTomcatEmbeddedServletContainer(tomcat);
            }

            @Override
            protected void postProcessContext(Context context) {
                ContextResource master = new ContextResource();
                ContextResource slave = new ContextResource();
                master.setName("jdbc/master");
                master.setType(DataSource.class.getName());
                master.setAuth("Container");
                master.setProperty("driverClassName", "oracle.jdbc.OracleDriver");
                master.setProperty("url", "jdbc:oracle:thin:@//211.171.200.80:1522/MANADEV");
                master.setProperty("username", "manna");
                master.setProperty("password", "Ekswnr59");
                master.setProperty("validationQuery","SELECT 1 FROM DUAL");
                master.setProperty("factory","org.apache.tomcat.jdbc.pool.DataSourceFactory");
                master.setProperty("jdbcInterceptors","org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer");

                slave.setName("jdbc/slave");
                slave.setType(DataSource.class.getName());
                slave.setAuth("Container");
                slave.setProperty("driverClassName", "oracle.jdbc.OracleDriver");
                slave.setProperty("url", "jdbc:oracle:thin:@//211.171.200.80:1522/MANADEV");
                slave.setProperty("username", "manna");
                slave.setProperty("password", "Ekswnr59");
                slave.setProperty("validationQuery","SELECT 1 FROM DUAL");
                slave.setProperty("factory","org.apache.tomcat.jdbc.pool.DataSourceFactory");
                slave.setProperty("jdbcInterceptors","org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer");

                context.getNamingResources().addResource(master);
                context.getNamingResources().addResource(slave);
            }
        };
    }

    @Primary
    @Bean(name = "masterDataSource", destroyMethod = "close")
    public DataSource dataSourceMaster() throws NamingException {
        JndiTemplate jndiTemplate = new JndiTemplate();
        return (DataSource) jndiTemplate.lookup(jndiProdProperties.getJndiMaster());
    }

    @Primary
    @Bean(name = "Masterjdbc")
    @Autowired
    public JdbcTemplate masterJdbcTemplate(@Qualifier("masterDataSource") DataSource masterDataSource) {
        return new JdbcTemplate(masterDataSource);
    }


    @Bean(name = "slaveDataSource", destroyMethod = "close")
    public DataSource dataSourceSlave() throws NamingException {
        JndiTemplate jndiTemplate = new JndiTemplate();
        return (DataSource) jndiTemplate.lookup(jndiProdProperties.getJndiSlave());
    }

    @Bean(name = "Slavejdbc")
    @Autowired
    public JdbcTemplate slaveJdbcTemplate(@Qualifier("slaveDataSource") DataSource slaveDataSource) {
        return new JdbcTemplate(slaveDataSource);
    }


    @Primary
    @Bean("transactionManagerMaster")
    public PlatformTransactionManager transactionManagerMaster() throws NamingException {
        return new DataSourceTransactionManager(dataSourceMaster());
    }

    @Bean("transactionManagerSlave")
    public PlatformTransactionManager transactionManagerSlave() throws NamingException {
        return new DataSourceTransactionManager(dataSourceSlave());
    }


    /**
     * Creates a {@link MultiDataSourcePlatformConfigurator} for production with
     * two separate Databases.
     *
     * @return {@link MultiDataSourcePlatformConfigurator}
     * @throws NamingException
     */
    @Bean
    public MultiDataSourcePlatformConfigurator multiDataSourcePlatformConfigurator() throws NamingException {
        MultiDataSourcePlatformConfigurator multiDataSourcePlatformConfigurator = new MultiDataSourcePlatformConfiguratorImpl();
        multiDataSourcePlatformConfigurator.addDetails(Define.DATABASE_MASTER, DataSourceDetails.of(dataSourceMaster(), transactionManagerMaster()));
        multiDataSourcePlatformConfigurator.addDetails(Define.DATABASE_SLAVE, DataSourceDetails.of(dataSourceSlave(), transactionManagerSlave()));
        return multiDataSourcePlatformConfigurator;
    }

}
