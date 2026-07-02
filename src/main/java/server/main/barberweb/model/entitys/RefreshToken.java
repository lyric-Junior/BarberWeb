package server.main.barberweb.model.entitys;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "refresh_tokens")
public class RefreshToken {

    @Getter @Setter
    @Id
    private String token;

    @Getter @Setter
    private boolean used;

    @Getter @Setter
    private boolean revoked;

    @Getter @Setter
    private Instant createdAt;

    @Getter @Setter
    private Instant expiresAt;

    @Getter @Setter
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
