package server.main.barberweb.controller;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.main.barberweb.model.dtos.AgendamentoDto;
import server.main.barberweb.model.dtos.UserDto;
import server.main.barberweb.model.dtos.register.RegRequest;
import server.main.barberweb.model.dtos.register.RegResponse;
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
    public ResponseEntity<?> definirHorario(@RequestParam Long id, @RequestBody @Valid UserDto user) {
        return ResponseEntity.ok(service.definirHorario(user, id));
    }

    @GetMapping("/listarParaMim")
    public List<AgendamentoDto> listarParaMim() {
        return scheduleService.listarParaMim();
    }

    @PostMapping("/")
    public ResponseEntity<RegResponse> cadastrarUsuario(RegRequest request) {
        return ResponseEntity.ok(service.cadastrarUsuario(request));
    }
}
