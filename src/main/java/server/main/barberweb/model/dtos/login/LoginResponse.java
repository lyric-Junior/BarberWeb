package server.main.barberweb.model.dtos.login;


import lombok.Getter;
import lombok.Setter;
import server.main.barberweb.model.entitys.Role;

import java.util.UUID;

@Getter @Setter
public class LoginResponse {

    private String refreshToken;

    private String accessToken;

    private String username;

    private String email;

    private String numero;

    private UUID userId;

    private Role role;
}
