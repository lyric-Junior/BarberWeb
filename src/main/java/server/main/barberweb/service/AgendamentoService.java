package server.main.barberweb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import server.main.barberweb.model.entitys.Agendamento;
import server.main.barberweb.repository.AgendamentoRepository;
import server.main.barberweb.repository.AgendamentoSpecification;

import java.util.List;

@Service
public class AgendamentoService {

    @Autowired
    private AgendamentoRepository repo;




}
