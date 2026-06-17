package server.main.barberweb.model.dtos.login;


import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LoginResponse {

    private String refreshToken;

    private String accessToken;

    private String username;

    private String email;

    private String numero;
}
