package server.main.barberweb.controller;


import jakarta.validation.Valid;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.main.barberweb.model.dtos.AgendamentoDto;
import server.main.barberweb.model.dtos.UserDto;
import server.main.barberweb.service.AgendamentoService;
import server.main.barberweb.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService service;
    @Autowired
    private AgendamentoService scheduleService;

    @PostMapping("/definirHorario/{id}")
    public ResponseEntity<?> definirHorario(@RequestParam Long id, @RequestBody @Valid UserDto user, Authentication auth) {
        service.definirHorario(user, id);
        return ResponseEntity.ok("Your schedule is already setted up!");
    }

    @GetMapping("/listarParaMim")
    public List<AgendamentoDto> listarParaMim() {
        return scheduleService.listarParaMim();
    }

    @PostMapping("/")
    public ResponseEntity<String> definirHorario() {

    }

}
