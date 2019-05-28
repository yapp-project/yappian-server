package com.yapp.web1;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class Application {

    public static final String APPLICATION_LOCATIONS =
                    "spring.config.location="
                    + "classpath:application.yml,"
                    + "/app/config/yappian/s3.yml,"
                    + "/app/config/yappian/google.yml,"
                    + "/app/config/yappian/real-db.yml,"
                    + "classpath:local-ssl.yml"
            ;

    public static void main(String[] args) {
        new SpringApplicationBuilder(Application.class)
                .properties(APPLICATION_LOCATIONS)
                .run(args);
    }

}
