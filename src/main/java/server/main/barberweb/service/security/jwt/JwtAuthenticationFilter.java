package server.main.barberweb.service.security.jwt;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    /*
     * Header padrão Bearer.
     */
    private static final String AUTH_HEADER = "Authorization";

    private static final String TOKEN_PREFIX = "Bearer ";

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        /*
         * Nunca confiar diretamente no header.
         */
        String authHeader = request.getHeader(AUTH_HEADER);

        /*
         * Se não existir Bearer token:
         * continua fluxo normalmente.
         *
         * O endpoint protegido será tratado pelo Spring Security.
         */
        if (authHeader == null || !authHeader.startsWith(TOKEN_PREFIX)) {

            filterChain.doFilter(request, response);
            return;
        }

        try {

            /*
             * Remove "Bearer ".
             */
            String token = authHeader.substring(TOKEN_PREFIX.length());

            /*
             * Proteção adicional.
             */
            if (!jwtService.isAccessToken(token)) {

                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            Claims claims = jwtService.extractClaims(token);

            UUID userId = UUID.fromString(
                    claims.getSubject()
            );

            String role = claims.get("role", String.class);

            /*
             * Prefixo ROLE_ é obrigatório para hasRole().
             */
            SimpleGrantedAuthority authority =
                    new SimpleGrantedAuthority("ROLE_" + role);

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            userId,
                            null,
                            List.of(authority)
                    );

            /*
             * Injeta usuário autenticado no contexto.
             */
            SecurityContextHolder
                    .getContext()
                    .setAuthentication(authentication);

        } catch (Exception ex) {

            /*
             * Nunca propagar erro interno do JWT.
             */
            SecurityContextHolder.clearContext();

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            return;
        }

        filterChain.doFilter(request, response);
    }
}