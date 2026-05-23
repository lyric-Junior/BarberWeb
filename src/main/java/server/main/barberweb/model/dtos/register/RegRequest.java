package server.main.barberweb.model.dtos.register;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RegRequest {


    private String username;

    private String email;

    private String password;

    private String numero;
}
