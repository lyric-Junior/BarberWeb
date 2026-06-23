package server.main.barberweb.service.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import server.main.barberweb.model.entitys.RefreshToken;
import server.main.barberweb.repository.RefreshTokenRepo;

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

    @Autowired
    private RefreshTokenRepo refreshRepo;


    public void detectReplay(
            RefreshToken token
    ) {
        //Verifica se o token ja foi utilizado
        if (token.isUsed()) {

            refreshRepo
                    .revokeAllById(
                            token.getUser().getId()
                    );

            throw new RuntimeException(
                    "Replay attack detected"
            );
        }
    }
    //Sera configurada apartir de variaveis de ambiente
    private static final long REFRESH_EXPIRATION =
            30L * 24 * 60 * 60;

    //Nao utilize JWT para refresh token, torne em algo mais simples porem seguro
    public String generateRefreshToken() {

        return UUID.randomUUID().toString()
                + UUID.randomUUID();
    }
    //Funcao para tornar token como utilizado
    public void markAsUsed(String token) {

        RefreshToken obj = refreshRepo.findById(token)
                .orElseThrow(() -> new BadCredentialsException("Bad credentials!"));

        if (obj.isUsed()) {
            throw new RuntimeException(
                    "Bad credentials!");
        }
    }

    //Revogando token
    public void revokeToken(String token) {
        RefreshToken obj = refreshRepo.findById(token)
                .orElseThrow(() -> new RuntimeException("Bad credentials!"));

        obj.setRevoked(true);
        refreshRepo.save(obj);
    }

    public void saveRefreshToken(RefreshToken token) {

        token.setCreatedAt(Instant.now());
        token.setExpiresAt(Instant.now().plusSeconds(REFRESH_EXPIRATION));
        refreshRepo.save(token);
    }

    public void validateReplayAttack(String token) {
        RefreshToken obj = refreshRepo.findById(token)
                .orElseThrow(() -> new BadCredentialsException("Bad credentials!"));

        if (obj.isUsed()) {
            detectReplay(obj);
        }
    }

    /*
     * Verifica expiração.
     */
    public boolean isExpired(Instant expiresAt) {

        return expiresAt.isBefore(Instant.now());
    }
}