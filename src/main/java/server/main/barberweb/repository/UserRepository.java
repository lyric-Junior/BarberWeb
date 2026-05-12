package server.main.barberweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.main.barberweb.model.entitys.User;

import java.util.List;

public interface UserRepository
extends JpaRepository<User, Long> {

    List<User> findByUsername(String username);
}
