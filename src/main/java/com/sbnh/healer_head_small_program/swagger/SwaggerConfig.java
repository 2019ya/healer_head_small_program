package com.sbnh.healer_head_small_program.swagger;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Description Swagger配置类
 * @Author peter
 * @Date 2020/04/27 17:15
 */
@ComponentScan("com.sbnh.healer_head_small_program.controller")
@Configuration
@EnableSwagger2
public class SwaggerConfig {


    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).enable(true)
                .select().apis(RequestHandlerSelectors.any()).paths(PathSelectors.any()).build()
                .apiInfo(apiInfo());
    }

    /**
     * 添加摘要信息
     */
    private ApiInfo apiInfo() {
        // 用ApiInfoBuilder进行定制
        return new ApiInfoBuilder()
                .title("标题：healer_head小程序")
                .description("描述：healer_head小程序接口文档")
                .contact(new Contact("罗伟华", null, null))
                .version("版本号: 1.0.0")
                .build();
    }
}
