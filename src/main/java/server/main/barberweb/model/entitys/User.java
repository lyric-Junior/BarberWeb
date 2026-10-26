package server.main.barberweb.model.entitys;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.validator.constraints.br.CPF;

import java.util.HashSet;
import java.util.Set;
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
    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinTable(
            name = "usuario_contato",
            joinColumns = @JoinColumn(name = "users_id"),
            inverseJoinColumns = @JoinColumn(name = "contatos_id"))
    private Set<Contato> contatos = new HashSet<>();

    @Getter @Setter
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] foto;
}