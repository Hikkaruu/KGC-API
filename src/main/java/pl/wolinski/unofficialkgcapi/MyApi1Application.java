package pl.wolinski.unofficialkgcapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class MyApi1Application {

    public static void main(String[] args) {
        SpringApplication.run(MyApi1Application.class, args);
    }

    //Klasa Docket służy do automatycznego generowania dokumentacji interfejsów API (np. RESTful API) opartych na Springu.
    // Docket jest częścią biblioteki Swagger, która umożliwia tworzenie i udostępnianie dokumentacji API w czytelny sposób.
    @Bean
    public Docket apiDocket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                // Wyświetlane w Swaggerze zostają tylko api z podanej ścieżki (nasz pierwszy warunek)
                .paths(PathSelectors.ant("/api/**"))
                // Przeszukuje czy w danym package znajduje się api pod podaną ścieżką (nasz drugi warunek)
                .apis(RequestHandlerSelectors.basePackage("pl.wolinski.unofficialkgcapi"))
                .build()
                // Wstawienie informacji o API
                .apiInfo(apiInfo());
    }

    // Klasa ApiInfo służy do dostarczania metadanych (informacji o API) dla generowanej dokumentacji Swagger.
    // Zawiera różne informacje o API, takie jak tytuł, opis, wersja, kontakt do autora, licencję itp
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Unofficial KGC API")
                .description("Unofficial KGC Database \n" +
                        "by [Discord: dextermorgan2137](https://discord.gg/7kjdaq7wuG)")
                .version("1.0.0")
                .build();
    }

}
