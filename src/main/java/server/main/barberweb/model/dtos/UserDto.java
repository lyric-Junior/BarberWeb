package server.main.barberweb.model.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter
public class UserDto {

    private UUID id;

    private String username;

    private String numero;

    private String email;
}
