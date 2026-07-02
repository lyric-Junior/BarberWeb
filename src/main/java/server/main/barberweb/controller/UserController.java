package server.main.barberweb.controller;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.main.barberweb.model.dtos.agendamento.AgendamentoDto;
import server.main.barberweb.model.dtos.agendamento.ScheduleRequest;
import server.main.barberweb.model.dtos.user.ProfissionalDto;
import server.main.barberweb.model.entitys.Servico;
import server.main.barberweb.service.AgendamentoService;
import server.main.barberweb.service.ServiceService;
import server.main.barberweb.service.UserService;

import java.time.LocalDate;
import java.time.LocalTime;
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
    public ResponseEntity<?> definirHorario(@RequestBody ScheduleRequest request) {
        return ResponseEntity.ok(service.definirHorario(request));
    }

    @PutMapping("/cancelarHorario/{id}")
    public ResponseEntity<String> cancelarHorario(@Valid @PathVariable Long id) {
        return ResponseEntity.ok(service.cancelarHorario(id));
    }

//    @GetMapping("/listarParaMim")
//    public List<AgendamentoDto> listarParaMim() {
//        return scheduleService.listarParaMim();
//    }

    @GetMapping("/listarServicos")
    public List<Servico> listarServicos() {
        return serviceService.listarServicos();
    }

    @GetMapping("/listarHorariosDisponiveis")
    public ResponseEntity<List<LocalTime>> listarHorariosDisponiveis(@RequestParam @Valid LocalDate data) {
        return ResponseEntity.ok(service.listarHorariosDisponiveis(data));
    }

    @GetMapping("/listarProfissionais")
    public ResponseEntity<List<ProfissionalDto>> listarProfissionais(LocalDate data, LocalTime horario) {
        return ResponseEntity.ok(service.listarProfissionais(data, horario));
    }

    @GetMapping("/listarPorHorario")
    public ResponseEntity<AgendamentoDto> listarPorHorario(@Valid @RequestParam LocalTime horario, @Valid @RequestParam LocalDate data) {
        return ResponseEntity.ok(scheduleService.findOneSchedule(data, horario));
    }
}
