package com.o2osys.pos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.client.Netty4ClientHttpRequestFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.AsyncRestTemplate;

import com.o2osys.pos.config.YamlPropertySourceFactory;

@SpringBootApplication
@EnableScheduling
//@EnableAsync
@PropertySource(value = {"classpath:/config/config.yaml","classpath:/config/config-${spring.profiles}.yaml"}
, ignoreResourceNotFound = true
, factory = YamlPropertySourceFactory.class)
public class ApiApplication {
    //extends AsyncConfigurerSupport {

    // 로그
    private final Logger log = LoggerFactory.getLogger(ApiApplication.class);

    @Value("${property.local}")
    private String propLocal;

    @Value("${property.dev}")
    private String propDev;

    @Value("${property.prod}")
    private String propProd;

    @Value("${property.prod1}")
    private String propProd1;

    @Value("${property.prod2}")
    private String propProd2;

    @Value("${spring.profiles}")
    private String profiles;

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }

    @Bean(name = "messageSource")
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages/messages");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setUseCodeAsDefaultMessage(true);
        messageSource.setCacheMillis(5000);

        return messageSource;
    }

    //Netty 에서 제공 되는 Async 모듈
    @Bean
    public AsyncRestTemplate getAsyncRestTemplate() {
        return new AsyncRestTemplate(new Netty4ClientHttpRequestFactory());
        //        return new AsyncRestTemplate(new HttpComponentsAsyncClientHttpRequestFactory());
    }


    /*
	@Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(2);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("GithubLookup-");
        executor.initialize();
        return executor;
    }
     */

    @Bean
    public CommandLineRunner runner() {

        return (a) -> {

            log.info("CommandLineRunner: " + propLocal);
            log.info("CommandLineRunner: " + propDev);
            log.info("CommandLineRunner: " + propProd);
            log.info("CommandLineRunner: " + propProd1);
            log.info("CommandLineRunner: " + propProd2);
            log.info("CommandLineRunner: " + profiles);

        };
    };
}
