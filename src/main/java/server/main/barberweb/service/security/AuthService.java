package server.main.barberweb.service.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import server.main.barberweb.model.dtos.login.LoginRequest;
import server.main.barberweb.model.dtos.login.LoginResponse;
import server.main.barberweb.model.entitys.RefreshToken;
import server.main.barberweb.model.entitys.User;
import server.main.barberweb.repository.UserRepository;
import server.main.barberweb.service.security.jwt.JwtService;

import java.time.Instant;

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

    @Autowired
    private JwtService jwtService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepo;

    /*
     * Fluxo de login.
     */
    public LoginResponse login(LoginRequest request) {
        User user =  userRepo.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Bad credentials"));

        boolean passwordMatches =
                passwordEncoder.matches(
                        request.getPassword(),
                        user.getPassword()
                );

        if (!passwordMatches) {
            throw new BadCredentialsException("Bad credentials");
        }

        String acessToken = jwtService.generateAccessToken(
                user.getId(),
                user.getRole().name()
        );
        /*
         * Gerar refresh token.
         */

        String refreshToken = refreshTokenService.generateRefreshToken();

        RefreshToken obj =  new RefreshToken();

        obj.setRevoked(false);
        obj.setToken(refreshToken);
        obj.setUsed(false);
        obj.setUser(user);

        //Persistir refresh token
        refreshTokenService.saveRefreshToken(obj);

        LoginResponse response = new LoginResponse();

        response.setAcessToken(acessToken);
        response.setRefreshToken(refreshToken);


        /*
         * Persistir refresh token.
         */

        /*
         * Retornar resposta.
         */
        return response;
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