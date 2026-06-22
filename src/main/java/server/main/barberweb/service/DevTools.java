package server.main.barberweb.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.main.barberweb.model.entitys.Role;
import server.main.barberweb.model.entitys.User;
import server.main.barberweb.repository.UserRepository;

import java.util.UUID;

@Service
public class DevTools {

    @Autowired
    private UserRepository userRepo;

    @Transactional
    public String tornarAdmin(UUID id) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("The user could not be found!"));
        user.setRole(Role.ADMIN);
        userRepo.save(user);

        return ("The user " + user.getUsername() + " is now admin");
    }

    @Transactional
    public String tornarDeveloper(UUID id) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("The user could not be found!"));
        user.setRole(Role.DEVELOPER);
        userRepo.save(user);

        return ("The user " + user.getUsername() + " is now a developer");
    }
}
