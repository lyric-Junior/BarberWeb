package server.main.barberweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import server.main.barberweb.model.entitys.Agendamento;

import java.util.List;

public interface AgendamentoRepository
extends JpaRepository<Agendamento, Long>, JpaSpecificationExecutor<Agendamento> {

}
