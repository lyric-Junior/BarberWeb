package server.main.barberweb.model.dtos;

import lombok.Getter;
import lombok.Setter;
import server.main.barberweb.model.entitys.Servico;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;
import java.util.UUID;

@Getter @Setter
public class ScheduleEdit {

    private Long id;

    private LocalTime horario;

    private LocalDate data;

    private UUID profissional;

    private UUID cliente;

    private Set<Servico> servicos;

    private boolean ativa;

    private boolean disponivel;

}
