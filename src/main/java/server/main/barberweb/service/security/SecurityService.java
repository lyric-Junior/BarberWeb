package server.main.barberweb.service.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import server.main.barberweb.service.security.jwt.JwtAuthenticationFilter;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityService {

    @Value("http://localhost:5173")
    private String corsOrigins;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(
                Arrays.stream(corsOrigins.split(","))
                        .map(String::trim)
                        .toList()
        );

        configuration.setAllowedMethods(
                List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
        );

        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {

        http
                // APIs REST com JWT normalmente não utilizam sessão HTTP.
                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                // CSRF é relevante principalmente para aplicações baseadas em sessão/cookie.
                .csrf(AbstractHttpConfigurer::disable)

                // Habilita CORS usando configuração padrão/customizada.
                .cors(Customizer.withDefaults())

                // Define permissões de endpoints

                .authorizeHttpRequests(auth -> auth

                        //Endpoints publicos de autenticacao
                        .requestMatchers(
                                "/auth/login",
                                "/auth/refresh",
                                "/auth/cadastrarUsuario"
                        ).permitAll()

                        // Apenas Admins
                        .requestMatchers("/admin/**")
                        .hasAnyRole("ADMIN", "DEVELOPER")

                        //Apenas DEVELOPER
                        .requestMatchers("/developer/**")
                        .hasRole("DEVELOPER")

                        .requestMatchers("/user")
                        .hasAnyRole("USER", "DEVELOPER", "ADMIN")

                        //Qualquer outro endpoint vai precisar de autenticacao
                        .anyRequest()
                        .authenticated()
                )

                //  Adiciona filtro JWT antes do filtro padrão do Spring Boot Security.

                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }

}
