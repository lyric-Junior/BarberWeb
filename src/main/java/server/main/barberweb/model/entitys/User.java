package server.main.barberweb.model.entitys;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.NumberFormat;

import java.util.UUID;

@Entity
@Table(name = "users")
public class User {

    @Getter @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Getter @Setter
    @Max(20)
    private String username;

    @Getter @Setter
    @Max(16)
    @Min(8)
    private String password;

    @Getter @Setter
    @CPF
    private String cpf;

    @NumberFormat
    @Getter @Setter
    private String numero;

    @Email
    @Getter @Setter
    private String email;

    @Getter @Setter
    @Enumerated(EnumType.STRING)
    private Role role;
}