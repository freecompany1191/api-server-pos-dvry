package com.o2osys.pos.config;

import java.util.ArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.o2osys.pos.common.constants.Define;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableWebMvc
@EnableSwagger2
public class SwaggerConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }


    @Bean
    public Docket api(){
        //API 명칭 정의
        ApiInfoBuilder apiInfo = new ApiInfoBuilder();
        apiInfo.title("만나 POS 연동 APIs");
        apiInfo.description("Link APIs");
        apiInfo.termsOfServiceUrl("");
        apiInfo.version("1.0");

        //HTTP 결과코드 메세지 정의
        ArrayList<ResponseMessage> responseMessages = new ArrayList<ResponseMessage>();
        responseMessages.add(new ResponseMessageBuilder()
                .code(200)
                .message(Define.HttpStatus.Message.OK)
                .build());
        responseMessages.add(new ResponseMessageBuilder()
                .code(400)
                .message(Define.HttpStatus.Message.BAD_REQUEST)
                .build());
        responseMessages.add(new ResponseMessageBuilder()
                .code(401)
                .message(Define.HttpStatus.Message.UNAUTHORIZED)
                .build());
        responseMessages.add(new ResponseMessageBuilder()
                .code(403)
                .message(Define.HttpStatus.Message.FORBIDDEN)
                .build());
        responseMessages.add(new ResponseMessageBuilder()
                .code(404)
                .message(Define.HttpStatus.Message.NOT_FOUND)
                .build());
        responseMessages.add(new ResponseMessageBuilder()
                .code(500)
                .message(Define.HttpStatus.Message.SERVER_ERROR)
                .responseModel(new ModelRef("Error"))
                .build());

        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false) // default response description 사용하지 않음
                /*
                .globalResponseMessage(RequestMethod.DELETE, responseMessages)
                .globalResponseMessage(RequestMethod.GET, responseMessages)
                .globalResponseMessage(RequestMethod.HEAD, responseMessages)
                .globalResponseMessage(RequestMethod.OPTIONS, responseMessages)
                .globalResponseMessage(RequestMethod.PATCH, responseMessages)
                .globalResponseMessage(RequestMethod.POST, responseMessages)
                .globalResponseMessage(RequestMethod.PUT, responseMessages)
                 */
                .select()
                //.apis(RequestHandlerSelectors.basePackage("com.common.controller")) // 해당 패키지만 필터
                //.paths(PathSelectors.ant("/api/common/**")) // 해당 request url 필터
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo.build());
    }

    /*
    private SpringSwaggerConfig springSwaggerConfig;

     *//**
     * Required to autowire SpringSwaggerConfig
     *//*
    @Autowired
    public void setSpringSwaggerConfig(SpringSwaggerConfig springSwaggerConfig) {
        this.springSwaggerConfig = springSwaggerConfig;
    }

      *//**
      * Every SwaggerSpringMvcPlugin bean is picked up by the swagger-mvc
      * framework - allowing for multiple swagger groups i.e. same code base
      * multiple swagger resource listings.
      *//*
    @Bean
    public SwaggerSpringMvcPlugin customImplementation() {
        return new SwaggerSpringMvcPlugin(this.springSwaggerConfig)
                .apiInfo(apiInfo())
                .includePatterns(".*?");
    }

    private ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfo("만나 외부 연동 APIs", "Dvry Con APIs", "", "", "", "");
        return apiInfo;
    }
       */

}
