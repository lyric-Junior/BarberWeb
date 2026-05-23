package server.main.barberweb.model.dtos.register;

import lombok.Getter;
import lombok.Setter;
import server.main.barberweb.model.entitys.Role;

@Getter @Setter
public class RegResponse {

    private Role role;
    private String username;
    private String message;
}
