package server.main.barberweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import server.main.barberweb.model.entitys.RefreshToken;

import java.util.UUID;

@Repository
public interface RefreshTokenRepo
extends JpaRepository<RefreshToken, String> {

    @Modifying
    @Query("""
    UPDATE RefreshToken rt
    SET rt.revoked = true
    WHERE rt.user.id = :userId
""")
    void revokeAllById(UUID userId);
}
