package org.example.authenticationandauthorization.securityconfig;

import org.springframework.boot.context.properties.ConfigurationProperties;



@ConfigurationProperties(prefix = "khalti")

public record KhaltiProperties(
        String secretKey,
        String lookupUrl

) {
}
