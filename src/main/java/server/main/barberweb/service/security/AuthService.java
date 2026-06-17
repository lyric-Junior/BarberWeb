package server.main.barberweb.service.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import server.main.barberweb.model.dtos.login.LoginRequest;
import server.main.barberweb.model.dtos.login.LoginResponse;
import server.main.barberweb.model.entitys.RefreshToken;
import server.main.barberweb.model.entitys.User;
import server.main.barberweb.repository.RefreshTokenRepo;
import server.main.barberweb.repository.UserRepository;
import server.main.barberweb.service.security.jwt.JwtService;

@Service
@RequiredArgsConstructor
public class AuthService {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private RefreshTokenRepo refreshRepo;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepo;


     // Fluxo de login.

    public LoginResponse login(LoginRequest request) {

        User user =  userRepo.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadCredentialsException("Invalid email or password"));

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
        //Gera o refreshToken

        String refreshToken = refreshTokenService.generateRefreshToken();

        RefreshToken obj =  new RefreshToken();

        obj.setRevoked(false);
        obj.setToken(refreshToken);
        obj.setUsed(false);
        obj.setUser(user);

        //Persistir refresh token
        refreshTokenService.saveRefreshToken(obj);

        LoginResponse response = new LoginResponse();

        response.setAccessToken(acessToken);
        response.setRefreshToken(refreshToken);
        response.setEmail(user.getEmail());
        response.setUsername(user.getUsername());
        response.setNumero(user.getNumero());
        // Retornar resposta

        return response;
    }

    // Fluxo para o refreshToken
    public LoginResponse refresh(String refreshToken) {

        RefreshToken refreshToken1 = refreshRepo.findByToken(refreshToken);

        if (refreshToken1 == null) {
            throw new BadCredentialsException("Bad credentials");
        }

        //Validar replay
        refreshTokenService.validateReplayAttack(refreshToken);

        // Validar expiração
        refreshTokenService.isExpired(refreshToken1.getExpiresAt());
        // Invalidar token antigo
        refreshToken1.setUsed(true);

        //User info
        User user = userRepo.findById(refreshToken1.getUser().getId())
                .orElseThrow(() -> new BadCredentialsException("Bad credentials!"));

        //Gerar novo refresh
        String refreshToken2 = refreshTokenService.generateRefreshToken();

        //Criar RefreshToken
        RefreshToken obj = new RefreshToken();

        //Getting and setting
        obj.setRevoked(false);
        obj.setToken(refreshToken2);
        obj.setUsed(false);
        obj.setUser(refreshToken1.getUser());


        //Persistir
        refreshTokenService.saveRefreshToken(obj);
        refreshTokenService.revokeToken(refreshToken1.getToken());
        //Persistir novo refreshToken

        LoginResponse response = new LoginResponse();

        response.setAccessToken(jwtService.generateAccessToken(user.getId(), user.getRole().name()));
        response.setRefreshToken(refreshToken2);

        return response;
    }

    public void logout(String refreshToken) {

        refreshTokenService.revokeToken(
                refreshToken
        );
    }
}