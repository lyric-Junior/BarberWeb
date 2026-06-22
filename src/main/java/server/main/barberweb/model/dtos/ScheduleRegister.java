package server.main.barberweb.model.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Getter @Setter
public class ScheduleRegister {

    private LocalTime horario;

    private LocalDate data;

    private UUID profissional;

    private boolean ativa;

    private boolean disponivel;

}
