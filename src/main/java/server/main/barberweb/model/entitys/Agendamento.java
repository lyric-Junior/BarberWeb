package server.main.barberweb.model.entitys;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

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
    @ManyToMany
    private List<Servico> servicos;

    @Getter
    @Setter
    private boolean ativa;
}
