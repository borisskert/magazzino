package de.borisskert.magazzino.config;

import de.borisskert.magazzino.security.KeycloakLogoutHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true
)
public class SecurityConfiguration {

    @Autowired
    private Environment environment;

//    @Autowired
//    private CustomOAuth2AuthorizedClientService customOAuth2AuthorizedClientService;

    @Autowired
    private KeycloakLogoutHandler keycloakLogoutHandler;

    @Bean
    public SecurityFilterChain mySecurityFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(requests -> requests
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/api/**").authenticated()
                        .anyRequest().authenticated()
                )
//                .oauth2Login(oauth2Login -> oauth2Login
//                        .authorizedClientService(customOAuth2AuthorizedClientService)
//                        .permitAll()
//                )
                .oauth2ResourceServer((oauth2) -> oauth2
                        .jwt(Customizer.withDefaults())
                )
                .oauth2Login(Customizer.withDefaults())
                .logout(logout -> logout.addLogoutHandler(keycloakLogoutHandler).logoutSuccessUrl("/"))
                .cors(configureCors())
                .csrf(AbstractHttpConfigurer::disable)
                .build();
    }

    private Customizer<CorsConfigurer<HttpSecurity>> configureCors() {
        if (isDevProfile()) {
            return enableCorsForDev();
        }

        return httpSecurityCorsConfigurer -> {
        };
    }

    private boolean isDevProfile() {
        Set<String> activeProfiles = Arrays.stream(environment.getActiveProfiles())
                .collect(Collectors.toSet());
        return activeProfiles.contains("dev");
    }

    private Customizer<CorsConfigurer<HttpSecurity>> enableCorsForDev() {
        return httpSecurityCorsConfigurer -> {
            CorsConfiguration configuration = new CorsConfiguration();
            configuration.setAllowedOrigins(List.of("*"));
            configuration.setAllowedMethods(List.of("*"));
            configuration.setAllowedHeaders(List.of("*"));

            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration("/**", configuration);

            httpSecurityCorsConfigurer.configurationSource(source);
        };
    }
}
