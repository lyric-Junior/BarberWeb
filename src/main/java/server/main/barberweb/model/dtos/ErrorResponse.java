package server.main.barberweb.model.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class ErrorResponse {

    @Getter @Setter
    private LocalDateTime timestamp;

    @Getter @Setter
    private Integer status;

    @Getter @Setter
    private String error;

    @Getter @Setter
    private String message;
}
