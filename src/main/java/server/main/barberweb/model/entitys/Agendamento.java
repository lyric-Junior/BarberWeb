package server.main.barberweb.model.entitys;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public class Agendamento {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @DateTimeFormat
    private LocalDateTime horario;

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
}
