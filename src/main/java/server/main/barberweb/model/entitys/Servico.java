package server.main.barberweb.model.entitys;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
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
    @NotNull
    @NotBlank
    private String name;

    @Getter @Setter
    @NotNull @NotBlank
    private String descricao;

    @Getter @Setter
    @NotNull @NotBlank
    private BigDecimal preço;

    @Getter @Setter
    @ManyToMany(mappedBy = "servicos")
    private Set<Agendamento> agendamentos = new HashSet<>();
}
