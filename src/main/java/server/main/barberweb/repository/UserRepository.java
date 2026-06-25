package server.main.barberweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import server.main.barberweb.model.entitys.Agendamento;
import server.main.barberweb.model.entitys.Role;
import server.main.barberweb.model.entitys.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository
extends JpaRepository<User, UUID>, JpaSpecificationExecutor<User> {

    User findOneByUsername(String username);

    boolean existsByRole(Role role);

    Optional<User> findByEmail(String email);

    @Query("""
    SELECT u
    FROM User u
    WHERE u.role = 'PROFISSIONAL'
    AND u.id NOT IN (
        SELECT a.profissional.id
        FROM Agendamento a
        WHERE a.data = :data
        AND a.horario = :horario
        AND a.profissional IS NOT NULL
    )
""")
    List<User> buscarProfissionaisDisponiveis(
            LocalDate data,
            LocalTime horario
    );
}
