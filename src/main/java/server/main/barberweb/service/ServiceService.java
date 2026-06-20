package server.main.barberweb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.main.barberweb.model.entitys.Servico;
import server.main.barberweb.repository.ServiceRepository;

import java.util.List;

@Service
public class ServiceService {

    @Autowired
    private ServiceRepository serviceRepository;

    public List<Servico> listarServicos() {
        return serviceRepository.findAll();
    }

    public String deletarServico(int id) {
        serviceRepository.deleteById(id);
        return ("Service deleted sucessfully!");
    }

    public String cadastrarServico(Servico servico) {

        Servico newServico = new Servico();

        newServico.setName(servico.getName());
        newServico.setDescricao(servico.getDescricao());
        newServico.setPreço(servico.getPreço());

        serviceRepository.save(newServico);
        return ("Service registered sucessfully!");
    }
}
