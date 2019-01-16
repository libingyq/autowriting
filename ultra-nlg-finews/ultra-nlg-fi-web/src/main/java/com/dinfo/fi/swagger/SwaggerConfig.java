package com.dinfo.fi.swagger;

import com.google.common.base.Predicate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * SwaggerConfig
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /**
     * SpringBoot默认已经将classpath:/META-INF/resources/和classpath:/META-INF/resources/webjars/映射
     * 所以该方法不需要重写，如果在SpringMVC中，可能需要重写定义（我没有尝试）
     * 重写该方法需要 extends WebMvcConfigurerAdapter
     *
     */
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("swagger-ui.html")
//                .addResourceLocations("classpath:/META-INF/resources/");
//
//        registry.addResourceHandler("/webjars/**")
//                .addResourceLocations("classpath:/META-INF/resources/webjars/");
//    }

    /**
     * 可以定义多个组，比如本类中定义把test和demo区分开了
     * （访问页面就可以看到效果了）
     *
     */
    @Bean
    public Docket testApi() {

        Predicate<RequestHandler> predicate = new Predicate<RequestHandler>() {
            @Override
            public boolean apply(RequestHandler input) {
                Class<?> declaringClass = input.declaringClass();
//                if (declaringClass == BasicErrorController.class)// 排除
//                    return false;
                if(declaringClass.isAnnotationPresent(Api.class)) // 被注解的类
                    return true;
                if(input.isAnnotatedWith(ApiOperation.class)) // 被注解的方法
                    return true;
                return false;
            }
        };

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(testApiInfo())
                .useDefaultResponseMessages(false)
                .select()
                .apis(predicate)
                .build();

    }





    private ApiInfo testApiInfo() {

        return new ApiInfoBuilder()
                .title("ultra-nlg-web HTTP接口测试页面")//大标题
                .description("用来进行ultra-nlg-web模块的功能测试")//详细描述
                .version("1.0")//版本
                .termsOfServiceUrl("NO terms of service")
                .license("The Apache License, Version 2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                .build();
    }

}
