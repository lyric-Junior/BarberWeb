package server.main.barberweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import server.main.barberweb.model.entitys.Agendamento;
import server.main.barberweb.model.entitys.Role;
import server.main.barberweb.model.entitys.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository
extends JpaRepository<User, UUID>, JpaSpecificationExecutor<User> {

    User findOneByUsername(String username);

    boolean existsByRole(Role role);

    Optional<User> findByEmail(String email);
}
