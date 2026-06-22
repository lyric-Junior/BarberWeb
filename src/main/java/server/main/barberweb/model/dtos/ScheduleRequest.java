package server.main.barberweb.model.dtos;

import lombok.Getter;
import lombok.Setter;
import server.main.barberweb.model.entitys.Agendamento;
import server.main.barberweb.model.entitys.Servico;
import server.main.barberweb.model.entitys.User;

import java.util.Set;
import java.util.UUID;

@Getter @Setter
public class ScheduleRequest {


    private Long id;

    private UUID profissional;

    private Set<Servico> servicos;
}
