package server.main.barberweb.repository;

import org.springframework.data.jpa.domain.Specification;
import server.main.barberweb.model.entitys.Role;
import server.main.barberweb.model.entitys.User;

public class UserSpecification {

    public static Specification<User> profissionalEquals(String role) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("role"), role);
    }
}
