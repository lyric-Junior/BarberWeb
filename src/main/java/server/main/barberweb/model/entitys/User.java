package server.main.barberweb.model.entitys;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.NumberFormat;

@Entity
@Table(name = "users")
public class User {

    @Getter @Setter
    @Id
    private Long id;

    @Getter @Setter
    @Max(20)
    private String username;

    @Getter @Setter
    private boolean admin;

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
}