package server.main.barberweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.main.barberweb.model.dtos.UserDto;
import server.main.barberweb.model.entitys.Agendamento;
import server.main.barberweb.service.AdminTools;
import server.main.barberweb.service.AgendamentoService;
import server.main.barberweb.service.ServiceService;
import server.main.barberweb.service.UserService;

import java.util.List;
import java.util.UUID;

@RequestMapping("/admin")
@RestController
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private AgendamentoService scheduleService;

    @Autowired
    private ServiceService serviceService;

    @Autowired
    private AdminTools adminTools;

    @GetMapping("/listarUsuarios")
    public ResponseEntity<List<UserDto>> listarUsuarios() {
        return ResponseEntity.ok(userService.listarUsuarios());
    }

    @GetMapping("/listarAgendamentos")
    public ResponseEntity<?> listarAgendamentos() {
        return ResponseEntity.ok(scheduleService.listarAgendamentos());
    }

    @PostMapping("/editarUsuario")
    public ResponseEntity<String> editarUsuario(@RequestBody UserDto user) {
        return ResponseEntity.ok(userService.editarUsuario(user));
    }

    @DeleteMapping("/deletarUsuario/{id}")
    public ResponseEntity<String> deletarUsuario(@PathVariable UUID id) {
        return ResponseEntity.ok(userService.deletarUsuario(id));
    }

    @GetMapping("/listarAgendamentosPorFiltro")
    public ResponseEntity<List<Agendamento>> listarPorFiltro(@RequestParam String cliente, @RequestParam String profissional, @RequestParam boolean disponivel) {
        return ResponseEntity.ok(scheduleService.listarPorFiltro(cliente, profissional, disponivel));
    }

    @PostMapping("/cancelarAgendamento")
    public ResponseEntity<String> cancelarAgendamento(@RequestParam Long id) {
        return ResponseEntity.ok(adminTools.cancelarAgendamento(id));
    }

    @PostMapping("/editarAgendamento")
    public ResponseEntity<?> editarAgendamento(@RequestBody Agendamento body) {
        return ResponseEntity.ok(adminTools.editarAgendamento(body));
    }

    @PostMapping("/tornarDisponivel")
    public ResponseEntity<String> tornarDisponivel(@RequestParam Long id) {
        return ResponseEntity.ok(adminTools.tornarDisponivel(id));
    }

    @PostMapping("/criarAgendamento")
    public ResponseEntity<String> criarAgendamento(@RequestBody Agendamento schedule) {
        return ResponseEntity.ok(scheduleService.cadastrarAgendamento(schedule));
    }

    @PostMapping("/deletarServico")
    public ResponseEntity<String> deletarServico(@RequestParam Integer id) {
        serviceService.deletarServico(id);

        return ResponseEntity.ok("The service " + id + " was deleted!");
    }
}
