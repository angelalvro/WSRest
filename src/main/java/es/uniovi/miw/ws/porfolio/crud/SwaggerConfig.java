package es.uniovi.miw.ws.porfolio.crud;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.*;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket apiDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select().apis(RequestHandlerSelectors
                        .basePackage("es.uniovi.miw.ws.porfolio.crud.controllers"))
                .paths(PathSelectors.any()).build().apiInfo(getApiInfo())
                .useDefaultResponseMessages(false);
    }
    private ApiInfo getApiInfo() {
        return new ApiInfo(
                "WS CRUD Portfolio","Web Services - MIW","1.0","",
                new Contact(
                        "Angel Alvarez Rodriguez",
                        "uo225174@uniovi.es",
                        "uo225174@uniovi.es"
                ),
                "LICENSE","LICENSE URL", Collections.emptyList()
        );
    }
}
