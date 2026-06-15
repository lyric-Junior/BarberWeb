package server.main.barberweb.controller;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.main.barberweb.model.dtos.AgendamentoDto;
import server.main.barberweb.model.dtos.UserDto;
import server.main.barberweb.model.entitys.Servico;
import server.main.barberweb.repository.ServiceRepository;
import server.main.barberweb.service.AgendamentoService;
import server.main.barberweb.service.ServiceService;
import server.main.barberweb.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService service;

    @Autowired
    private ServiceService serviceService;

    @Autowired
    private AgendamentoService scheduleService;

    @PostMapping("/definirHorario/{id}")
    public ResponseEntity<?> definirHorario(@PathVariable Long id, @Valid UserDto user) {
        return ResponseEntity.ok(service.definirHorario(user, id));
    }

    @PutMapping("/cancelarHorario/{id}")
    public ResponseEntity<String> cancelarHorario(@Valid @PathVariable Long id) {
        return ResponseEntity.ok(service.cancelarHorario(id));
    }

    @GetMapping("/listarParaMim")
    public List<AgendamentoDto> listarParaMim() {
        return scheduleService.listarParaMim();
    }

    @GetMapping("/listarServicos")
    public List<Servico> listarServicos() {
        return serviceService.listarServicos();
    }
}
