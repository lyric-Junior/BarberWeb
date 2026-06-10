package server.main.barberweb.model.entitys;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "agendamentos")
public class Agendamento {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @DateTimeFormat
    private LocalTime horario;

    @Getter @Setter
    private LocalDate data;

    @Getter
    @Setter
    private boolean disponivel;

    @Getter
    @Setter
    private String cliente;

    @Getter
    @Setter
    private String profissional;

    @Getter @Setter
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "agendamento_servico",
            joinColumns = @JoinColumn(name = "agendamento_id"),
            inverseJoinColumns = @JoinColumn(name = "servico-id")
    )
    private Set<Servico> servicos = new HashSet<>();

    @Getter
    @Setter
    private boolean ativa;
}
