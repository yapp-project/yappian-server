package com.yapp.web1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class Application {

    public static final String APPLICATION_LOCATIONS =
                    "spring.config.location="
                    + "classpath:application.yml,"
                    + "/app/config/yappian/s3.yml";

    public static void main(String[] args) {
        new SpringApplicationBuilder(Application.class)
                .properties(APPLICATION_LOCATIONS)
                .run(args);
    }

}
