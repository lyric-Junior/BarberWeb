package server.main.barberweb.repository;

import org.springframework.data.jpa.domain.Specification;
import server.main.barberweb.model.entitys.Agendamento;

public class AgendamentoSpecification {

    public static Specification<Agendamento> clienteContains(String cliente) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("cliente")),
                        "%" + cliente.toLowerCase() + "%"
                );
    }

    public static Specification<Agendamento> profissionalEquals(String profissional) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("profissional"), profissional);
    }

    public static Specification<Agendamento> ativaEquals(boolean ativa) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("ativa"), ativa);
    }

    public static Specification<Agendamento> disponivelEquals(boolean disponivel) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("disponivel"), disponivel);
    }
}