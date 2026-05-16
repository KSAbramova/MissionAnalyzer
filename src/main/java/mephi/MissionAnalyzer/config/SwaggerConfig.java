package mephi.MissionAnalyzer.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Mission Archive API")
                        .description("API для загрузки, просмотра, удаления и формирования отчетов по миссиям")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Абрамова Ксения Сергеевна")
                        )
                );
    }
}
