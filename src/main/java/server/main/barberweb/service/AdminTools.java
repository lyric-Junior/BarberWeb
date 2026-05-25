package server.main.barberweb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.main.barberweb.model.dtos.UserDto;
import server.main.barberweb.model.entitys.Agendamento;
import server.main.barberweb.model.entitys.User;
import server.main.barberweb.repository.AgendamentoRepository;
import server.main.barberweb.repository.UserRepository;

import java.util.List;
import java.util.UUID;

@Service
public class AdminTools {

    @Autowired
    private AgendamentoRepository agendamentoRepo;

    @Autowired
    private UserRepository userRepo;

    //USERS SECTION

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
        User user = userRepo.findById(id)
                        .orElseThrow(() -> new RuntimeException("The user could not be found!"));

        userRepo.deleteById(id);

        return ("User deleted sucessfully!");
    }

    //AGENDAMENTO SECTION


    public String cancelarAgendamento(Long id) {
        Agendamento vaga = agendamentoRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("The scheduling was not found!"));
        vaga.setAtiva(false);
        return ("The scheduling " + vaga + " was canceled!");
    }

    public String editarAgendamento(Agendamento body) {
        Agendamento vaga = agendamentoRepo.findById(body.getId())
                .orElseThrow(() -> new RuntimeException("The scheduling was not found!"));

        vaga.setProfissional(body.getProfissional());
        vaga.setCliente(body.getCliente());
        vaga.setHorario(body.getHorario());
        vaga.setData(body.getData());
        vaga.setAtiva(body.isAtiva());
        vaga.setDisponivel(body.isDisponivel());
        return ("The scheduling " + vaga.getId() + " was updated!");
    }

    public String tornarDisponível(Long id) {
        Agendamento vaga = agendamentoRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("The scheduling could not be found!"));

        vaga.setDisponivel(true);

        return  ("The scheduling " + vaga.getId() + " is now open!");
    }
}
