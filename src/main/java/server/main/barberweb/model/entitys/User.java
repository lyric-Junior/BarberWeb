package server.main.barberweb.model.entitys;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.validator.constraints.br.CPF;

import java.sql.Blob;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User {

    @Getter @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Getter @Setter
    @Size(max = 20)
    @NotNull @NotBlank
    private String username;

    @Getter @Setter
    @Size(min = 8)
    @NotNull @NotBlank
    private String password;

    @Getter @Setter
    @CPF
    private String cpf;

    @Getter @Setter
    private String numero;

    @Getter @Setter
    @Email
    @NotNull @NotBlank
    private String email;

    @Getter @Setter
    @Enumerated(EnumType.STRING)
    private Role role;

    @Getter @Setter
    private Blob foto;
}