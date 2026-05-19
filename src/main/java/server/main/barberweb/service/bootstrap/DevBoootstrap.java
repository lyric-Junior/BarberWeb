package server.main.barberweb.service.bootstrap;


import lombok.RequiredArgsConstructor;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import server.main.barberweb.model.entitys.Role;
import server.main.barberweb.model.entitys.User;
import server.main.barberweb.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class DevBoootstrap implements CommandLineRunner {


    private final UserRepository userRepo;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        boolean exists =  userRepo.existsByRole(Role.DEVELOPER);

        if (exists) {
            return;
        }

        User user = new User();

        user.setUsername("Developer");
        user.setPassword(System.getenv("DEV_PASSWORD"));
        user.setRole(Role.DEVELOPER);
        user.setEmail(System.getenv("DEV_EMAIL"));
        user.set
        userRepo.save(user);
    }

}
