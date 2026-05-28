package server.main.barberweb.model.dtos.login;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

public class LoginRequest {

    @Getter @Setter
    @Email(message = "Invalid email")
    @NotBlank(message = "Email is required")
    private String email;

    @Getter @Setter
    @NotBlank(message = "Password is required")
    private String password;
}