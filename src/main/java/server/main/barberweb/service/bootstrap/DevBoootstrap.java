package server.main.barberweb.service.bootstrap;


import io.github.cdimascio.dotenv.Dotenv;
import lombok.RequiredArgsConstructor;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import server.main.barberweb.model.entitys.Role;
import server.main.barberweb.model.entitys.User;
import server.main.barberweb.repository.UserRepository;

import static server.main.barberweb.config.Config.DEV_EMAIL;
import static server.main.barberweb.config.Config.DEV_PASSWORD;

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

        String hashPassword = passwordEncoder.encode(DEV_PASSWORD);


        user.setUsername("Developer");
        user.setPassword(hashPassword);
        user.setRole(Role.DEVELOPER);
        user.setEmail(DEV_EMAIL);
        userRepo.save(user);
    }

}
