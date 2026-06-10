package server.main.barberweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.main.barberweb.model.entitys.Agendamento;
import server.main.barberweb.model.entitys.Servico;

import java.util.List;

public interface ServiceRepository
    extends JpaRepository<Servico, Integer> {

    List<Servico> findAllById();
}
