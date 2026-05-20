package server.main.barberweb.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import server.main.barberweb.model.dtos.AgendamentoDto;
import server.main.barberweb.model.dtos.UserDto;
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

    @Autowired
    private PasswordEncoder passwordEncoder;

    private AgendamentoDto convertScheduleDto(Agendamento agendamento) {
        AgendamentoDto dto = new AgendamentoDto();

        dto.setId(agendamento.getId());
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

    public String definirHorario(Long id, UserDto dto) {
        Agendamento agendamento  = agendamentoRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("The Schedule is no longer available or doesn't exist!"));

        agendamento.setCliente(dto.getUsername());
        agendamento.setDisponivel(false);
        agendamento.setAtiva(true);

        return ("Your scheduling is done!");
    }

    public String registrarUsuario(User dto) {
        User user = new User();

        String passwordHash = passwordEncoder.encode(dto.getPassword());

        user.setEmail(dto.getEmail());
        user.setNumero(dto.getNumero());

        return ("por enquanto");
    }



}
