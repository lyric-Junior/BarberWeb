package server.main.barberweb.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.main.barberweb.model.dtos.AgendamentoDto;
import server.main.barberweb.model.entitys.User;
import server.main.barberweb.repository.AgendamentoRepository;
import server.main.barberweb.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private AgendamentoRepository agendamentoRepo;

    public List<AgendamentoDto> listarParaMim() {
        return agendamentoRepo.findBy
    }


}
