package server.main.barberweb.model.entitys;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.validator.constraints.br.CPF;

import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Size(max = 20)
    private String username;

    @Size(min = 8)
    private String password;

    @CPF
    private String cpf;

    private String numero;

    @Email
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;
}