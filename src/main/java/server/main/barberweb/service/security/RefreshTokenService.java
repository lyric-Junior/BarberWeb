package server.main.barberweb.service.security;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

/*
 * Serviço responsável por:
 * - criar refresh token
 * - rotacionar refresh token
 * - revogar refresh token
 * - validar replay
 *
 * Aqui assumimos:
 * - repository já existe
 * - entity já existe
 */
@Service
@RequiredArgsConstructor
public class RefreshTokenService {


    public void detectReplay(
            RefreshToken token
    ) {

        /*
         * Token já utilizado anteriormente.
         *
         * Isso indica:
         * - possível vazamento
         * - possível roubo de sessão
         * - replay attack
         */
        if (token.isUsed()) {

            /*
             * Revoga todos tokens do usuário.
             *
             * Medida defensiva:
             * assume comprometimento da sessão.
             */
            refreshTokenRepository
                    .revokeAllByUserId(
                            token.getUser().getId()
                    );

            throw new RuntimeException(
                    "Replay attack detected"
            );
        }
    }

    /*
     * 30 dias.
     */
    private static final long REFRESH_EXPIRATION =
            30L * 24 * 60 * 60;

    /*
     * Gera token aleatório.
     *
     * Não utilizar JWT para refresh token
     * simplifica replay detection.
     */
    public String generateRefreshToken() {

        return UUID.randomUUID().toString()
                + UUID.randomUUID();
    }

    /*
     * Marca refresh token antigo como usado.
     *
     * IMPORTANTÍSSIMO:
     * replay detection depende disso.
     */
    public void markAsUsed(String token) {

        /*
         * Buscar token no banco.
         * token.setUsed(true)
         * salvar.
         */
    }

    /*
     * Revoga token explicitamente.
     */
    public void revokeToken(String token) {

        /*
         * token.setRevoked(true)
         */
    }

    /*
     * Detecta replay.
     *
     * Se token já foi usado:
     * possível roubo de sessão.
     */
    public void validateReplayAttack(String token) {

        /*
         * if token.used == true:
         *     invalidar sessão
         *     lançar exceção
         */
    }

    /*
     * Verifica expiração.
     */
    public boolean isExpired(Instant expiresAt) {

        return expiresAt.isBefore(Instant.now());
    }
}