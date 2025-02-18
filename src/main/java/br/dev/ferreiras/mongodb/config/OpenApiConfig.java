package br.dev.ferreiras.mongodb.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/** Support to OpenAPI 3.0
 * @author ricardo@ferreiras.dev.br
 * @version 2024.10.30.01
 * @since 1.0
 *
 */

@OpenAPIDefinition
@Configuration
public class OpenApiConfig{
  /**
   *
   * @return API UI
   */
  @Bean
  public OpenAPI defineOpenApi() {
    final Server server = new Server();
    server.setUrl("""
            http://127.0.0.1:8095
            """);
    server.setDescription("Development");

    final Contact myContact = new Contact();
    myContact.setName(":Ricardo Ferreira");
    myContact.setEmail("ricardo@ferreiras.dev.br");

    final Info information = new Info()
        .title("Reactive RESTFUL Api")
        .version("1.0.1")
        .description("Reactive Restful API with Spring Webflux and Mongo|DB")
        .contact(myContact)
        .license(new License()
            .name("Apache 2.0")
            .url("https://github.com/rnhc1000/springMongoDB/posts-ref")
        );

    return new OpenAPI()
        .info(information)
        .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
        .components(
            new Components()
                .addSecuritySchemes("bearerAuth", new SecurityScheme()
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("bearer")
                    .bearerFormat("JWT")
                )
        )
        .servers(List.of(server));
  }
}
