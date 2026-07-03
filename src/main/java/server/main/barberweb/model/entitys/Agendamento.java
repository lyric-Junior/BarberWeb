package server.main.barberweb.model.entitys;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
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
    @NotNull
    @DateTimeFormat
    private LocalTime horario;

    @NotNull
    @Getter @Setter
    private LocalDate data;

    @Getter
    @Setter
    private boolean disponivel;

    @Getter @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    private User cliente;

    @Getter @Setter
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private User profissional;

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
