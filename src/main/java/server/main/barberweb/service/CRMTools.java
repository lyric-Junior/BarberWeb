package server.main.barberweb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.main.barberweb.model.dtos.user.UserDto;
import server.main.barberweb.model.entitys.Agendamento;
import server.main.barberweb.model.entitys.User;
import server.main.barberweb.repository.AgendamentoRepository;
import server.main.barberweb.repository.UserRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class CRMTools {

    @Autowired
    private AgendamentoRepository scheduleRepo;

    @Autowired
    private UserRepository userRepo;


}
