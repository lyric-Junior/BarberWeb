package server.main.barberweb.service.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Service
public class JwtService {

    /*
     * Nunca hardcode secrets dentro do código.
     * Sempre carregar via variável de ambiente ou application.yml.
     *
     * IMPORTANTE:
     * HS256 exige uma chave suficientemente forte.
     * Recomenda-se pelo menos 256 bits.
     */
    @Value("${security.jwt.secret}")
    private String jwtSecret;

    /*
     * Access token curto reduz impacto de vazamento.
     */
    private static final long ACCESS_TOKEN_EXPIRATION = 15 * 60;

    /*
     * Gera a chave criptográfica utilizada pelo HS256.
     */
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(
                jwtSecret.getBytes(StandardCharsets.UTF_8)
        );
    }

    /*
     * Gera um JWT de acesso.
     *
     * Claims mínimas:
     * - sub  -> identificador do usuário
     * - role -> autorização
     * - type -> proteção adicional contra uso indevido
     * - jti  -> identificador único do token
     */
    public String generateAccessToken(
            UUID userId,
            String role
    ) {

        Instant now = Instant.now();

        return Jwts.builder()
                .setSubject(userId.toString())
                .claim("role", role)
                .claim("type", "access")
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(Date.from(now))
                .setExpiration(
                        Date.from(
                                now.plusSeconds(ACCESS_TOKEN_EXPIRATION)
                        )
                )
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /*
     * Faz parsing seguro do JWT.
     *
     * Aqui:
     * - valida assinatura
     * - valida integridade
     * - valida expiração
     */
    public Claims extractClaims(String token) {

        try {

            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

        } catch (JwtException ex) {

            /*
             * Nunca retornar detalhes internos do JWT.
             * Isso evita enumeração e fingerprinting.
             */
            throw new RuntimeException("Invalid token");
        }
    }

    /*
     * Extrai subject.
     */
    public UUID extractUserId(String token) {

        Claims claims = extractClaims(token);

        return UUID.fromString(claims.getSubject());
    }

    /*
     * Verifica se token expirou.
     */
    public boolean isExpired(String token) {

        Claims claims = extractClaims(token);

        return claims.getExpiration().before(new Date());
    }

    /*
     * Proteção adicional:
     * garante que access token não seja usado
     * como refresh token.
     */
    public boolean isAccessToken(String token) {

        Claims claims = extractClaims(token);

        return "access".equals(claims.get("type"));
    }
}