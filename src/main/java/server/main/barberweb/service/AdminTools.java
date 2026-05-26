package server.main.barberweb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.main.barberweb.model.entitys.Agendamento;
import server.main.barberweb.repository.AgendamentoRepository;

@Service
public class AdminTools {

    @Autowired
    private AgendamentoRepository agendamentoRepo;

    //Cancelar agendamento
    public String cancelarAgendamento(Long id) {
        Agendamento vaga = agendamentoRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("The scheduling was not found!"));
        vaga.setAtiva(false);
        return ("The scheduling " + vaga + " was canceled!");
    }

    public String editarAgendamento(Agendamento body) {
        Agendamento vaga = agendamentoRepo.findById(body.getId())
                .orElseThrow(() -> new RuntimeException("The scheduling was not found!"));

        vaga.setProfissional(body.getProfissional());
        vaga.setCliente(body.getCliente());
        vaga.setHorario(body.getHorario());
        vaga.setData(body.getData());
        vaga.setAtiva(body.isAtiva());
        vaga.setDisponivel(body.isDisponivel());
        return ("The scheduling " + vaga.getId() + " was updated!");
    }

    public String tornarDisponivel(Long id) {
        Agendamento vaga = agendamentoRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("The scheduling could not be found!"));

        vaga.setDisponivel(true);

        return  ("The scheduling " + vaga.getId() + " is now open!");
    }
}
