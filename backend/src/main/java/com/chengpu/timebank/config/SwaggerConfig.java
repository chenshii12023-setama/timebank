package com.chengpu.timebank.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger/OpenAPI 配置类
 * 配置 API 文档生成
 * 
 * @author TimeBank Team
 */
@Configuration
public class SwaggerConfig {
    
    /**
     * OpenAPI 配置 Bean
     * 
     * @return OpenAPI 实例
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("TimeBank API 文档")
                        .version("1.0.0")
                        .description("诚朴时间银行 RESTful API 接口文档")
                        .contact(new Contact()
                                .name("TimeBank Team")
                                .email("support@timebank.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")));
    }
}

