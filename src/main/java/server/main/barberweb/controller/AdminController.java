package server.main.barberweb.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.main.barberweb.model.dtos.agendamento.ScheduleEdit;
import server.main.barberweb.model.dtos.agendamento.ScheduleRegister;
import server.main.barberweb.model.dtos.user.EditUser;
import server.main.barberweb.model.dtos.user.UserDto;
import server.main.barberweb.model.entitys.Agendamento;
import server.main.barberweb.model.entitys.Servico;
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

    @GetMapping("/findUserById")
    public ResponseEntity<EditUser> findUserById(@RequestParam UUID id) {
        return ResponseEntity.ok(userService.findUserById(id));
    }

    @GetMapping("/listarUsuarios")
    public ResponseEntity<List<UserDto>> listarUsuarios() {
        return ResponseEntity.ok(userService.listarUsuarios());
    }

    @GetMapping("/listarUsuariosComFiltro")
    public ResponseEntity<List<UserDto>> listarUsuariosComFiltro(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String cpf) {
        return ResponseEntity.ok(userService.listarPorFiltro(username, email, cpf));
    }

    @GetMapping("/listarAgendamentos")
    public ResponseEntity<?> listarAgendamentos() {
        return ResponseEntity.ok(scheduleService.listarAgendamentos());
    }

    @GetMapping

    @PutMapping("/editarUsuario")
    public ResponseEntity<String> editarUsuario(@Valid @RequestBody UserDto user) {
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

    @PutMapping("/cancelarAgendamento")
    public ResponseEntity<String> cancelarAgendamento(@RequestParam Long id) {
        return ResponseEntity.ok(adminTools.cancelarAgendamento(id));
    }

    @DeleteMapping("/deletarAgendamento")
    public ResponseEntity<String> deletarAgendamento(@RequestParam Long id) {
        return ResponseEntity.ok(scheduleService.deletarAgendamento(id));
    }

    @PutMapping("/editarAgendamento")
    public ResponseEntity<?> editarAgendamento(@Valid @RequestBody ScheduleEdit body) {
        return ResponseEntity.ok(adminTools.editarAgendamento(body));
    }

    @PostMapping("/tornarDisponivel")
    public ResponseEntity<String> tornarDisponivel(@RequestParam Long id) {
        return ResponseEntity.ok(adminTools.tornarDisponivel(id));
    }

    @PostMapping("/criarAgendamento")
    public ResponseEntity<String> criarAgendamento(@Valid @RequestBody ScheduleRegister schedule) {
        return ResponseEntity.ok(scheduleService.cadastrarAgendamento(schedule));
    }

    @DeleteMapping("/deletarServico")
    public ResponseEntity<String> deletarServico(@RequestParam Integer id) {
        serviceService.deletarServico(id);

        return ResponseEntity.ok("The service " + id + " was deleted!");
    }

    @PostMapping("/cadastrarServico")
    public ResponseEntity<String> cadastrarServico(@Valid @RequestBody Servico service) {
        return ResponseEntity.ok(serviceService.cadastrarServico(service));
    }

    @PostMapping("/tornarProfissional")
    public ResponseEntity<String> tornarProfissional(@RequestParam UUID id) {
        return ResponseEntity.ok(adminTools.tornarProfissional(id));
    }

    @GetMapping("/listScheduleByClient")
    public ResponseEntity<List<Agendamento>> listScheduleByClient(@NotNull Long id) {
        return ResponseEntity.ok(adminTools.listarPorCliente(id));
    }
}
