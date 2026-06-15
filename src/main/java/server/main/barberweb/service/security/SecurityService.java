package server.main.barberweb.service.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
import server.main.barberweb.service.security.jwt.JwtAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityService {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
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
