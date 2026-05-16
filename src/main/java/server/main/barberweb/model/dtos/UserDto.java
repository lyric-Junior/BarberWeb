package server.main.barberweb.model.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserDto {

    private Long id;

    private String username;

    private String numero;

    private String email;
}
