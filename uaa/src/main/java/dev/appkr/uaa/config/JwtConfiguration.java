package dev.appkr.uaa.config;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.RSAKey;
import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

@Configuration
public class JwtConfiguration {

    private final UaaProperties uaaProperties;

    public JwtConfiguration(UaaProperties uaaProperties) {
        this.uaaProperties = uaaProperties;
    }

    @Bean
    public JWKSet jwkSet(UaaProperties uaaProperties) {
        KeyPair keyPair = new KeyStoreKeyFactory(
            new ClassPathResource(uaaProperties.getKeyStore().getName()), uaaProperties.getKeyStore().getPassword().toCharArray())
            .getKeyPair(uaaProperties.getKeyStore().getAlias());

        RSAKey.Builder builder = new RSAKey.Builder((RSAPublicKey) keyPair.getPublic())
            .keyUse(KeyUse.SIGNATURE)
            .algorithm(JWSAlgorithm.RS256)
            .keyID("jhipster-uaa-key");

        return new JWKSet(builder.build());
    }
}
