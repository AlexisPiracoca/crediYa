package pragma.crediya.user.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API's")
                        .description("API para la gestión de usuarios y solicitudes de préstamos")
                        .version("1.0.0")
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Servidor de Desarrollo")
                ))
                .addTagsItem(new Tag()
                        .name("Usuarios")
                        .description("Operaciones relacionadas con la gestión de usuarios"))
                .addTagsItem(new Tag()
                        .name("Solicitudes")
                        .description("Operaciones relacionadas con solicitudes de préstamos"));
    }
}
