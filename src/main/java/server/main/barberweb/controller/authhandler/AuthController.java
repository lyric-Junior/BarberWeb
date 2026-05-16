package server.main.barberweb.controller.authhandler;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
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
    public void login() {

        /*
         * Receber DTO.
         * Chamar service.
         */
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