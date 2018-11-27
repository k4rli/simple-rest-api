package com.mountbirch.security.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * Created by Kaur Laanemäe on 19/11/2018.
 * Mountbirch OÜ
 * kaur@mountbirch.com
 */
@Configuration
@EnableAuthorizationServer
public class AuthServerOAuth2Config extends AuthorizationServerConfigurerAdapter {

    private final String clientId;
    private final String clientSecret;
    private final Integer accessTokenValiditySeconds;
    private final Integer refreshTokenValiditySeconds;

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthServerOAuth2Config(@Value("${authentication.oauth2.clientId}") String clientId,
                                  @Value("${authentication.oauth2.clientSecret}") String clientSecret,
                                  @Value("${authentication.oauth2.accessTokenValiditySeconds}") int accessTokenValiditySeconds,
                                  @Value("${authentication.oauth2.refreshTokenValiditySeconds}") int refreshTokenValiditySeconds) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.accessTokenValiditySeconds = accessTokenValiditySeconds;
        this.refreshTokenValiditySeconds = refreshTokenValiditySeconds;
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer configurer) throws Exception {
        configurer
                .inMemory()
                .withClient(clientId)
                .secret(clientSecret)
                .authorizedGrantTypes("password", "refresh_token", "client_credentials")
                .scopes("all")
                .accessTokenValiditySeconds(accessTokenValiditySeconds).
                refreshTokenValiditySeconds(refreshTokenValiditySeconds);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(tokenStore)
                .authenticationManager(authenticationManager);
    }
}
