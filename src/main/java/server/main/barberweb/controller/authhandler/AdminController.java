package server.main.barberweb.controller.authhandler;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import server.main.barberweb.model.dtos.UserDto;
import server.main.barberweb.model.entitys.User;
import server.main.barberweb.service.AgendamentoService;
import server.main.barberweb.service.UserService;

import java.util.List;

@RequestMapping("/admin")
@RestController
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private AgendamentoService scheduleService;

    @GetMapping("/listarUsuarios")
    public ResponseEntity<List<User>> listarUsuarios() {
        return ResponseEntity.ok(userService.listarUsuarios());
    }

    @PostMapping("/editarUsuarios")
    public ResponseEntity<String> editarUsuario(@RequestBody UserDto user, Authentication auth) {
        userService.editarUsuario(user);
        return ResponseEntity.ok("The user has been edited sucessfully!");
    }
}
