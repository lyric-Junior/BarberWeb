package server.main.barberweb.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import server.main.barberweb.model.dtos.agendamento.AgendamentoDto;
import server.main.barberweb.model.dtos.agendamento.ScheduleRegister;
import server.main.barberweb.model.dtos.user.ProfissionalDto;
import server.main.barberweb.model.dtos.user.UserDto;
import server.main.barberweb.model.entitys.Agendamento;
import server.main.barberweb.model.entitys.User;
import server.main.barberweb.repository.AgendamentoRepository;
import server.main.barberweb.repository.AgendamentoSpecification;
import server.main.barberweb.repository.UserRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AgendamentoService {

    @Autowired
    private AgendamentoRepository repo;

    @Autowired
    private UserRepository userRepo;

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

    public AgendamentoDto findOneSchedule(LocalDate data, LocalTime horario)  {
        Agendamento appointment = repo.findOneAgendamentoByDateAndTime(data, horario)
                .stream()
                .findAny()
                .orElseThrow(() -> new RuntimeException("Appointment not found!"));

        return convertAgendamentoDto(appointment);
    }

    //Função extra não admitida dentro do sistema

//    //Lista para cliente
//    public List<AgendamentoDto> listarParaMim() {
//
//        Specification<Agendamento> spec = (root, query, criteriaBuilder) ->
//                criteriaBuilder.conjunction();
//
//        spec.and(
//                AgendamentoSpecification.disponivelEquals(true)
//        );
//
//
//        return repo.findAll(spec).stream()
//                .map(this::convertAgendamentoDto)
//                .collect(Collectors.toList());
//    }

    @Transactional
    public String cadastrarAgendamento(ScheduleRegister body) {

        Agendamento schedule = new Agendamento();

        schedule.setAtiva(body.isAtiva());
        schedule.setDisponivel(body.isDisponivel());
        schedule.setData(body.getData());
        schedule.setHorario(body.getHorario());

        repo.save(schedule);

        return ("The scheduling " + schedule.getId() + " has been registered!");
    }

    @Transactional
    public String deletarAgendamento(Long id) {
        repo.deleteById(id);
        return ("The schedule" + id + " has been deleted!");
    }

    private AgendamentoDto convertAgendamentoDto(Agendamento agendamento) {
        AgendamentoDto dto = new AgendamentoDto();

        User pro = userRepo.findById(agendamento.getProfissional().getId())
                        .orElseThrow(() -> new RuntimeException("The professioanl doesn't exists!"));

        dto.setId(agendamento.getId());
        dto.setData(agendamento.getData());
        dto.setHorario(agendamento.getHorario());
        dto.setProfissional(convertUserProfissional(pro));

        return dto;
    }

    private UserDto converUserDto(User user) {
        UserDto dto = new UserDto();

        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setUsername(user.getUsername());
        dto.setNumero(user.getNumero());
        dto.setRole(user.getRole());

        return dto;
    }

    private ProfissionalDto convertUserProfissional(User user) {
        ProfissionalDto dto = new ProfissionalDto();

        dto.setFoto(user.getFoto());
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());

        return dto;
    }

}
