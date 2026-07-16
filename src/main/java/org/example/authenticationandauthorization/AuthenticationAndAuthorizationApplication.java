package org.example.authenticationandauthorization;

import org.example.authenticationandauthorization.securityconfig.KhaltiProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
@EnableConfigurationProperties(KhaltiProperties.class)
public class AuthenticationAndAuthorizationApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthenticationAndAuthorizationApplication.class, args);
    }

}
