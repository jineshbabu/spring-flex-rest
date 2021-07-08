package com.jaj.jaj;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
//import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.InMemoryReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.client.web.server.UnAuthenticatedServerOAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
public class WebClientConfig {
//    @Bean("cr")
//    ReactiveClientRegistrationRepository getRegistration(
//            @Value("${spring.security.oauth2.client.provider.bael.token-uri}") String tokenUri,
//            @Value("${spring.security.oauth2.client.registration.bael.client-id}") String clientId,
//            @Value("${spring.security.oauth2.client.registration.bael.client-secret}") String clientSecret
//    ) {
//        ClientRegistration registration = ClientRegistration
//                .withRegistrationId("bael")
//                .tokenUri(tokenUri)
//                .clientId(clientId)
//                .clientSecret(clientSecret)
//                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
//                .build();
//        return new InMemoryReactiveClientRegistrationRepository(registration);
//    }

//    @Bean
//    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
//        http.authorizeExchange()
//                .anyExchange()
//                .authenticated()
//                .and()
//                .oauth2Login();
//        return http.build();
//    }
    @Bean
    WebClient webClient(ReactiveClientRegistrationRepository clientRegistrations) {
        ServerOAuth2AuthorizedClientExchangeFilterFunction oauth = new ServerOAuth2AuthorizedClientExchangeFilterFunction(clientRegistrations, new UnAuthenticatedServerOAuth2AuthorizedClientRepository());
        oauth.setDefaultClientRegistrationId("bael");
        return WebClient.builder()
                .filter(oauth)
                .build();
    }

    @Bean
    public SecurityWebFilterChain configure(ServerHttpSecurity http) throws Exception {
        return http
                .authorizeExchange(exchanges -> exchanges
                        .anyExchange().permitAll()
                        .and()
                        .exceptionHandling()
                        .authenticationEntryPoint((exchange, exception) ->
                                Mono.error(exception))
                )
                .csrf().disable()
                .headers().disable()
                .logout().disable()
                .build();
    }

}