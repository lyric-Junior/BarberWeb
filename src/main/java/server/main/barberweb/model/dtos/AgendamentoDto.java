package server.main.barberweb.model.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter @Setter
public class AgendamentoDto {

    private Long id;

    private UserDto profissional;

    private LocalDate data;

    private LocalTime horario;

    private boolean disponivel;

    private boolean ativa;
}
