package server.main.barberweb.model.dtos.user;

import lombok.Getter;
import lombok.Setter;
import server.main.barberweb.model.entitys.Role;

import java.util.UUID;

@Getter @Setter
public class UserDto {

    private UUID id;

    private String username;

    private String numero;

    private String email;

    private Role role;
}
