package server.main.barberweb.service.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

        boolean isUsed = refreshRepo.findByToken(token).isUsed();

        if (isUsed) {
            throw new RuntimeException(
                    "Refresh token already used");
        }
    }

    //Revogando token
    public void revokeToken(String token) {
        RefreshToken obj = refreshRepo.findByToken(token);

        obj.setRevoked(true);
        refreshRepo.save(obj);
    }

    public void saveRefreshToken(RefreshToken token) {
        RefreshToken obj = refreshRepo.findByToken(token.getToken());

        if (obj == null || obj.isUsed()) {
            throw new RuntimeException("Refresh token already used");
        }
        obj.setCreatedAt(Instant.now());
        obj.setExpiresAt(Instant.now().plusSeconds(REFRESH_EXPIRATION));
    }

    public void validateReplayAttack(String token) {
        RefreshToken obj = refreshRepo.findByToken(token);

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