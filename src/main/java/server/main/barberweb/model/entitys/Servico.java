package server.main.barberweb.model.entitys;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "services")
public class Servico {

    @Getter @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @Getter @Setter
    private String name;

    @Getter @Setter
    private String descricao;

    @Getter @Setter
    private Double preço;

    @Getter @Setter
    @ManyToMany(mappedBy = "servicos")
    private Set<Agendamento> agendamentos = new HashSet<>();
}
