package server.main.barberweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
//import server.main.barberweb.model.entitys.Agendamento;
import server.main.barberweb.model.entitys.Servico;

//import java.util.List;
import java.util.Optional;

//Some not used functions must be in commented

public interface ServiceRepository
    extends JpaRepository<Servico, Integer> {

    //List<Servico> findAllById(Integer id);

    Optional<Servico> findByName(String name);
}
