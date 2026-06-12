package server.main.barberweb.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import server.main.barberweb.model.dtos.UserDto;
import server.main.barberweb.model.dtos.register.RegRequest;
import server.main.barberweb.model.dtos.register.RegResponse;
import server.main.barberweb.model.entitys.Agendamento;
import server.main.barberweb.model.entitys.Role;
import server.main.barberweb.model.entitys.User;
import server.main.barberweb.repository.AgendamentoRepository;
import server.main.barberweb.repository.UserRepository;

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

    public String deletarUsuario(UUID id) {
        userRepo.findById(id)
                .orElseThrow(
                        () -> new RuntimeException("The user could not be found!"));

        userRepo.deleteById(id);

        return ("User deleted sucessfully!");
    }

    public String definirHorario(UserDto dto, Long scheduleId) {
        Agendamento schedule = agendamentoRepo.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("The schedule could not be found!"));

        if (!schedule.isAtiva() || schedule.isDisponivel()) {
            throw new RuntimeException("The schedule is not available!");
        }

        schedule.setDisponivel(false);
        schedule.setAtiva(true);
        schedule.setCliente(dto.getUsername());

        return ("Your schedule is already setted up!");
    }



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

        response.setRole(user.getRole());
        response.setUsername(user.getUsername());
        response.setMessage("Usuário cadastrado com sucesso!");

        return (response);
    }

    private UserDto convertUserDto(User user) {
        UserDto dto = new UserDto();

        dto.setEmail(user.getEmail());
        dto.setNumero(user.getNumero());
        dto.setId(user.getId());

        return dto;
    }
}
