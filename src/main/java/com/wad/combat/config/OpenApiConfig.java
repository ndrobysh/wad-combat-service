package com.wad.combat.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI combatServiceOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Combat Gacha")
                        .description("Gère la simulation de combats automatiques entre monstres et l'historique des affrontements.")
                        .version("1.0.0"));
    }
}
