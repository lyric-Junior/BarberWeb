package server.main.barberweb.model.dtos.dashboard;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class LastTimeUsed {

    private Long id;

    private String username;

    private LocalDate data;

}
