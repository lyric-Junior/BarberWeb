package server.main.barberweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.main.barberweb.model.entitys.Role;
import server.main.barberweb.model.entitys.User;

import java.util.List;
import java.util.UUID;

public interface UserRepository
extends JpaRepository<User, UUID> {

    List<User> findByUsername(String username);

    boolean existsByRole(Role role);
}
