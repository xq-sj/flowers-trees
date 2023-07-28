package com.sj.config.knife4j;

import com.github.xiaoymin.knife4j.spring.extension.OpenApiExtensionResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import javax.annotation.Resource;

/**
 * @author xiaoqi
 */
@Slf4j
@Configuration
@EnableSwagger2WebMvc
public class Knife4jConfiguration implements WebMvcConfigurer {

    /**
     * 【重要】指定Controller包路径
     */
    private String basePackage = "com.sj.controller";
    /**
     * 分组名称
     */
    private String groupName = "sj";
    /**
     * 主机名
     */
    private String host = "https://blog.sjun7.com";
    /**
     * 标题
     */
    private String title = "花树博客在线API文档";
    /**
     * 简介
     */
    private String description = "花树博客在线API文档";
    /**
     * 服务条款URL
     */
    private String termsOfServiceUrl = "http://www.apache.org/licenses/LICENSE-2.0";
    /**
     * 联系人
     */
    private String contactName = "花树";
    /**
     * 联系网址
     */
    private String contactUrl = "https://blog.sjun7.com";
    /**
     * 联系邮箱
     */
    private String contactEmail = "2860253651@qq.com";
    /**
     * 版本号
     */
    private String version = "1.0.0";

    @Resource
    private OpenApiExtensionResolver openApiExtensionResolver;

    public Knife4jConfiguration() {
        log.debug("加载配置类：Knife4jConfiguration");
    }

    @Bean
    public Docket docket() {
        String groupName = "1.0.0";
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .host(host)
                .apiInfo(apiInfo())
                .groupName(groupName)
                .select()
                .apis(RequestHandlerSelectors.basePackage(basePackage))
                .paths(PathSelectors.any())
                .build()
                .extensions(openApiExtensionResolver.buildExtensions(groupName));
        return docket;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(title)
                .description(description)
                .termsOfServiceUrl(termsOfServiceUrl)
                .contact(new Contact(contactName, contactUrl, contactEmail))
                .version(version)
                .build();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**", "/img.icons/**", "/swagger-resources/**", "/v2/api-docs").addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("/swagger/**").addResourceLocations("classpath:/static/swagger/");
        registry.addResourceHandler("/static/**").addResourceLocations("/static/");
    }
}