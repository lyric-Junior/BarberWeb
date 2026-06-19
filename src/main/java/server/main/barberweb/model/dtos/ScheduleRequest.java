package server.main.barberweb.model.dtos;

import lombok.Getter;
import lombok.Setter;
import server.main.barberweb.model.entitys.Agendamento;
import server.main.barberweb.model.entitys.Servico;

import java.util.Set;

@Getter @Setter
public class ScheduleRequest {


    private Long id;

    private Set<Servico> servicos;
}
