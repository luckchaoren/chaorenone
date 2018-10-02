package com.chaoren.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by lichao on 2018/9/25.
 */
@EnableSwagger2
@Configuration
public class SwaggerConfiguration {

//    @Value("${swagger.basePackage}")
//    @Value(value = "com.mongo.demo.controller")
//    private String basePackage; //controller接口所在包

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.chaoren.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("超人项目组 1.0对接接口适配")//标题
                .description("提供可视化接口文档与接口测试工具")//描述
                .termsOfServiceUrl("https://www.baidu.com")//（不可见）条款地址
//                .contact(new Contact("超人","https://www.baidu.com/","123@qq.com"))//作者信息
                .license("©版权所有，超人必究")
                .version("6.6.6")//版本号
                .build();
    }

}
