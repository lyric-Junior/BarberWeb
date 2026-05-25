package server.main.barberweb.model.entitys;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "refresh_tokens")
@Getter @Setter
public class RefreshToken {

    @Id
    private String token;
    private boolean used;
    private boolean revoked;
    private Instant createdAt;
    private Instant expiresAt;
    private User user;
}
