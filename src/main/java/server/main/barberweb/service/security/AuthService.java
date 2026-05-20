package server.main.barberweb.service.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import server.main.barberweb.model.dtos.login.LoginRequest;
import server.main.barberweb.model.entitys.User;
import server.main.barberweb.repository.UserRepository;
import server.main.barberweb.service.security.jwt.JwtService;

/*
 * Serviço principal da autenticação.
 *
 * Responsável por:
 * - login
 * - refresh
 * - logout
 *
 * Apenas orquestra.
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtService jwtService;

    private final RefreshTokenService refreshTokenService;

    private final PasswordEncoder passwordEncoder;

    private UserRepository userRepo;

    /*
     * Fluxo de login.
     */
    public void login(LoginRequest request) {
        User user =  userRepo.findByE
        /*
         * Validar senha usando BCrypt.
         */

        /*
         * Gerar access token.
         */

        /*
         * Gerar refresh token.
         */

        /*
         * Persistir refresh token.
         */

        /*
         * Retornar resposta.
         */
    }

    /*
     * Fluxo de refresh.
     */
    public void refresh(String refreshToken) {

        /*
         * Validar replay.
         */

        /*
         * Validar expiração.
         */

        /*
         * Marcar token antigo como usado.
         */

        /*
         * Gerar novo refresh token.
         */

        /*
         * Gerar novo access token.
         */

        /*
         * Persistir novo refresh token.
         */
    }

    /*
     * Logout simples.
     */
    public void logout(String refreshToken) {

        refreshTokenService.revokeToken(
                refreshToken
        );
    }
}