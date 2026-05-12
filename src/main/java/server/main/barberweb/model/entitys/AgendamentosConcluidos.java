package server.main.barberweb.model.entitys;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "agendamentos_concluidos")
public class AgendamentosConcluidos {

    @Getter
    @Setter
    @Id
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

    @Getter
    @Setter
    private boolean ativa;

    @Getter @Setter
    private LocalDate dataFinalizacao;
}
