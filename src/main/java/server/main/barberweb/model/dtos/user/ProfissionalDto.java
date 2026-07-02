package server.main.barberweb.model.dtos.user;

import lombok.Getter;
import lombok.Setter;

import java.sql.Blob;
import java.util.UUID;

@Getter @Setter
public class ProfissionalDto {

    private String username;

    private UUID id;

    private Blob foto;

}
