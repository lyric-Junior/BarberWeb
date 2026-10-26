package server.main.barberweb.model.entitys;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.NumberFormat;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "contatos")
public class Contato {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private UUID id;

    @Getter @Setter
    @NumberFormat()
    private String numero;

    @Getter @Setter
    private CtCategoria categoria;

    @Getter @Setter
    @ManyToMany(mappedBy = "contatos")
    private Set<User> users = new HashSet<>();
}
