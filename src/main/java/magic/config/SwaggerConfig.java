package magic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * @author : Narcssus
 * @date : 2019/11/25 22:07
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig implements WebMvcConfigurer {
    @Bean
    public Docket createRestApi() {

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("magic"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("要用魔法打败魔法")
                .description("要用魔法打败魔法")
                .termsOfServiceUrl("https://blog.csdn.net/u012468264")
                .contact(new Contact("narcssus", "https://me.csdn.net/fans/u012468264", "lyl_hit@163.com"))
                .version("1.0")
                .build();
    }
}
