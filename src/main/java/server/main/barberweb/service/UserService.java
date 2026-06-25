package server.main.barberweb.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import server.main.barberweb.model.dtos.agendamento.ScheduleRequest;
import server.main.barberweb.model.dtos.user.UserDto;
import server.main.barberweb.model.dtos.register.RegRequest;
import server.main.barberweb.model.dtos.register.RegResponse;
import server.main.barberweb.model.entitys.Agendamento;
import server.main.barberweb.model.entitys.Role;
import server.main.barberweb.model.entitys.User;
import server.main.barberweb.repository.AgendamentoRepository;
import server.main.barberweb.repository.UserRepository;
import server.main.barberweb.repository.UserSpecification;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;
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

    //USERS SECTION
    public List<UserDto> listarUsuarios() {
        return userRepo.findAll().stream()
                .map(this::convertUserDto)
                .collect(Collectors.toList());
    }

    public List<LocalTime> listarHorariosDisponiveis(LocalDate data) {
        return agendamentoRepo.findHorariosDisponiveis(data);
    }

    @Transactional
    public String editarUsuario(UserDto dto) {
        User user = userRepo.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("The user could not be found!"));

        user.setEmail(dto.getEmail());
        user.setUsername(dto.getUsername());
        user.setNumero(dto.getNumero());
        user.setId(dto.getId());

        userRepo.save(user);

        return ("The user was edited successfully!");
    }

    @Transactional
    public String deletarUsuario(UUID id) {
        userRepo.findById(id)
                .orElseThrow(
                        () -> new RuntimeException("The user could not be found!"));

        userRepo.deleteById(id);

        return ("User deleted sucessfully!");
    }

    @Transactional
    public String definirHorario(ScheduleRequest request) {

        Agendamento schedule = agendamentoRepo.findById(request.getId())
                        .orElseThrow(() -> new RuntimeException("The appointment could not be found!"));

        if (!schedule.isAtiva() || !schedule.isDisponivel()) {
            throw new RuntimeException("The appointment is not available");
        }

        if (schedule.getHorario().isBefore(LocalTime.now())) {
            throw new RuntimeException("The appointment is no more available!");
        }

        boolean indisponivel = agendamentoRepo.existsByProfissionalIdAndDataAndHorario(
                schedule.getProfissional().getId(),
                schedule.getData(),
                schedule.getHorario()
        );

        if (indisponivel) {
            throw new  RuntimeException("Good try!");
        }

        UUID id = (UUID) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User customer = userRepo.findById(id)
                        .orElseThrow(() -> new RuntimeException("The user could not be found!"));

        User pro = userRepo.findById(request.getProfissional())
                .orElseThrow(() -> new RuntimeException("The professional doesn't exists!"));

        schedule.setDisponivel(false);
        schedule.setProfissional(pro);
        schedule.setCliente(customer);
        schedule.setServicos(request.getServicos());

        agendamentoRepo.save(schedule);

        return ("Your appointment is already setted up!");
    }

    @Transactional
    public String cancelarHorario(Long id) {
        Agendamento agendamento = agendamentoRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("the appointment could not be found!"));

        agendamento.setDisponivel(true);
        agendamento.setAtiva(true);
        agendamento.setServicos(null);
        agendamento.setCliente(null);
        agendamento.setProfissional(null);

        return ("The appointment is cancelled!");
    }


    @Transactional
    public RegResponse cadastrarUsuario(RegRequest request) {
        //Verificar se o usuário ja existe
        User userStored = userRepo.findOneByUsername(request.getUsername());

        //Seguindo essa regra nenhum nome pode ser igual, então tem que ser um nome maior.

        if (userStored != null) {
            throw new RuntimeException("The user already exists!");
        }

        User user = new User();

        //Hash da senha
        String senhaHash = passwordEncoder.encode(request.getPassword());

        //Definindo usuário
        user.setNumero(request.getNumero());
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setRole(Role.USER);
        user.setPassword(senhaHash);

        userRepo.save(user);

        //Montamos a response
        RegResponse response = new RegResponse();

        response.setUsername(user.getUsername());
        response.setMessage("User registered successfully!");

        return (response);
    }

    public List<UserDto> listarProfissionais(LocalDate data, LocalTime horario) {

        return userRepo.buscarProfissionaisDisponiveis(data, horario).stream()
                .map(this::convertUserDto)
                .collect(Collectors.toList());
    }

    private UserDto convertUserDto(User user) {
        UserDto dto = new UserDto();

        dto.setEmail(user.getEmail());
        dto.setNumero(user.getNumero());
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setRole(user.getRole());

        return dto;
    }
}
