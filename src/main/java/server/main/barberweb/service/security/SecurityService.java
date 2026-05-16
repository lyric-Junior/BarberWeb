package server.main.barberweb.service.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
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


    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {

        http

                /*
                 * APIs REST com JWT normalmente não utilizam sessão HTTP.
                 */
                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                /*
                 * CSRF é relevante principalmente para aplicações
                 * baseadas em sessão/cookie.
                 *
                 * Como utilizamos JWT stateless:
                 * CSRF pode ser desativado.
                 */
                .csrf(csrf -> csrf.disable())

                /*
                 * Habilita CORS usando configuração padrão/customizada.
                 */
                .cors(Customizer.withDefaults())

                /*
                 * Define permissões de endpoints.
                 */
                .authorizeHttpRequests(auth -> auth

                        /*
                         * Endpoints públicos.
                         */
                        .requestMatchers(
                                "/auth/login",
                                "/auth/refresh"
                        ).permitAll()

                        /*
                         * Apenas ADMIN.
                         */
                        .requestMatchers("/admin/**")
                        .hasRole("ADMIN")

                        /*
                         * Apenas DEVELOPER.
                         */
                        .requestMatchers("/developer/**")
                        .hasRole("DEVELOPER")

                        /*
                         * Qualquer outro endpoint:
                         * exige autenticação.
                         */
                        .anyRequest()
                        .authenticated()
                )

                /*
                 * Adiciona filtro JWT antes do filtro padrão
                 * do Spring Security.
                 */
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }

}
