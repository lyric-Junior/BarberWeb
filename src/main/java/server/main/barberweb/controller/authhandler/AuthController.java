package server.main.barberweb.controller.authhandler;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.main.barberweb.model.dtos.login.LoginRequest;
import server.main.barberweb.model.dtos.login.LoginResponse;
import server.main.barberweb.service.security.AuthService;

/*
 * Controller REST da autenticação.
 *
 * Não colocar lógica de negócio aqui.
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /*
     * Login.
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest dto) {
        return ResponseEntity.ok(authService.login(dto));
    }

    /*
     * Refresh token.
     *
     * Refresh token enviado via header:
     * X-Refresh-Token
     */
    @PostMapping("/refresh")
    public void refresh(
            @RequestHeader("X-Refresh-Token")
            String refreshToken
    ) {

        authService.refresh(refreshToken);
    }

    /*
     * Logout.
     */
    @PostMapping("/logout")
    public void logout(
            @RequestHeader("X-Refresh-Token")
            String refreshToken
    ) {

        authService.logout(refreshToken);
    }
}