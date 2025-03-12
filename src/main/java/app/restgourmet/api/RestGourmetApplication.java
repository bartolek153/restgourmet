package app.restgourmet.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

import app.restgourmet.api.commondata.service.spec.IStorageService;
import app.restgourmet.api.config.StorageProperties;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@SpringBootApplication
@EnableCaching
@EnableConfigurationProperties(StorageProperties.class)
public class RestGourmetApplication {
  public static void main(String[] args) {
    SpringApplication.run(RestGourmetApplication.class, args);
  }

  @Bean
  OpenAPI customOpenAPI(@Value("${openapi.service.title}") String serviceTitle,
      @Value("${openapi.service.version}") String serviceVersion) {
    return new OpenAPI()
        .components(
            new Components()
                .addSecuritySchemes("bearer-key",
                    new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .description("Description")
                        .bearerFormat("JWT")))
        .security(List.of(new SecurityRequirement().addList("bearer-key")))

        .info(new Info().title(serviceTitle).version(serviceVersion).description("Teste"));
  }

  @Bean
	CommandLineRunner init(IStorageService storageService) {
		return (args) -> {
			storageService.init();
		};
	}
}
