package server.main.barberweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import server.main.barberweb.model.entitys.Contato;

import java.util.List;
import java.util.UUID;

@Repository
public interface ContatoRepository
    extends JpaRepository<Contato, UUID>, JpaSpecificationExecutor<Contato> {

    @Query("""
    SELECT * FROM Contato
    WHERE users=
""")
    List<Contato>

}
