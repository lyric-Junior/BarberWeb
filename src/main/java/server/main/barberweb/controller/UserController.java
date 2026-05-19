package server.main.barberweb.controller;


import jakarta.validation.Valid;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import server.main.barberweb.model.dtos.AgendamentoDto;
import server.main.barberweb.model.dtos.UserDto;
import server.main.barberweb.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping("/definirHorario/{id}")
    public String definirHorario(@RequestParam Long id, @RequestBody @Valid UserDto user, Authentication auth) {
        return service.definirHorario(id, user);
    }

    @GetMapping("/listarParaMim")
    public List<AgendamentoDto> listarParaMim() {
        return service.listarParaMim();
    }

}
