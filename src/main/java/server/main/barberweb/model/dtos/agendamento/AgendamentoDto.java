package server.main.barberweb.model.dtos.agendamento;

import lombok.Getter;
import lombok.Setter;
import server.main.barberweb.model.dtos.user.UserDto;

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
