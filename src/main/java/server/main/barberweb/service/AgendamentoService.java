package server.main.barberweb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import server.main.barberweb.model.dtos.AgendamentoDto;
import server.main.barberweb.model.entitys.Agendamento;
import server.main.barberweb.repository.AgendamentoRepository;
import server.main.barberweb.repository.AgendamentoSpecification;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AgendamentoService {

    @Autowired
    private AgendamentoRepository repo;

    public List<Agendamento> listarAgendamentos() {
        return repo.findAll();
    }

    //SPECIFICATION JpaRepository
    public List<Agendamento> listarPorFiltro(String cliente, String profissional, boolean disponivel) {

        Specification<Agendamento> spec = (root, query, criteriaBuilder) ->
                criteriaBuilder.conjunction();

        if (cliente != null & !cliente.isBlank()) {
            spec = spec.and(
                    AgendamentoSpecification.clienteContains(cliente)
            );
        }

        if (profissional != null & !profissional.isBlank()) {
            spec = spec.and(
                    AgendamentoSpecification.profissionalEquals(profissional)
            );
        }

        if (disponivel) {
            spec = spec.and(
                    AgendamentoSpecification.ativaEquals(true)
            );
        }

        if (!disponivel) {
            spec = spec.and(
                    AgendamentoSpecification.ativaEquals(false)
            );
        }

        return repo.findAll(spec);
    }

    //Lista para cliente
    public List<AgendamentoDto> listarParaMim() {

        Specification<Agendamento> spec = (root, query, criteriaBuilder) ->
                criteriaBuilder.conjunction();

        spec.and(
                AgendamentoSpecification.disponivelEquals(true)
        );


        return repo.findAll(spec).stream()
                .map(this::convertAgendamentoDto)
                .collect(Collectors.toList());
    }

    public String cadastrarAgendamento(Agendamento body) {

        Agendamento schedule = new Agendamento();

        schedule.setProfissional(body.getProfissional());
        schedule.setCliente(body.getCliente());
        schedule.setHorario(body.getHorario());
        schedule.setAtiva(body.isAtiva());
        schedule.setDisponivel(body.isDisponivel());

        repo.save(schedule);

        return ("The scheduling " + schedule.getId() + " has been registered!");
    }

    public String deletarAgendamento(Long id) {
        repo.deleteById(id);
        return ("The schedule" + id + " has been deleted!");
    }

    private AgendamentoDto convertAgendamentoDto(Agendamento agendamento) {
        AgendamentoDto dto = new AgendamentoDto();

        dto.setId(agendamento.getId());
        dto.setData(agendamento.getData());
        dto.setHorario(agendamento.getHorario());
        dto.setProfissional(agendamento.getProfissional());
        return dto;
    }

}
