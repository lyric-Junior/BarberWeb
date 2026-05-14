package server.main.barberweb.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import server.main.barberweb.model.dtos.AgendamentoDto;
import server.main.barberweb.model.entitys.Agendamento;
import server.main.barberweb.model.entitys.User;
import server.main.barberweb.repository.AgendamentoRepository;
import server.main.barberweb.repository.AgendamentoSpecification;
import server.main.barberweb.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private AgendamentoRepository agendamentoRepo;

    private AgendamentoDto convertScheduleDto(Agendamento agendamento) {
        AgendamentoDto dto = new AgendamentoDto();

        dto.setHorario(agendamento.getHorario());
        dto.setData(agendamento.getData());
        dto.setProfissional(agendamento.getProfissional());
        dto.setAtiva(agendamento.isAtiva());
        dto.setDisponivel(agendamento.isDisponivel());

        return dto;
    }

    public List<AgendamentoDto> listarParaMim() {
        Specification<Agendamento> spec = (root, query, criteriaBuilder) ->
                criteriaBuilder.conjunction();

        spec.and(AgendamentoSpecification.disponivelEquals(true));

        List<AgendamentoDto> dtoList = agendamentoRepo.findAll(spec).stream()
                .map(this::convertScheduleDto)
                .toList(); //Dá pra escrever somente toList em vez de collect(Collectors.toList())

        if (dtoList.isEmpty()) {
            throw new RuntimeException("There are no available scheduling now!");
        }

        return dtoList;
    }

    public


}
