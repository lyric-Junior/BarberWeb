package server.main.barberweb.repository;

import org.springframework.data.jpa.domain.Specification;
import server.main.barberweb.model.entitys.User;

public class UserSpecification {

    public static Specification<User> usernameContains(String username) {
        return (root, query, cb) ->
                cb.like(cb.lower(root.get("username")),
                        "%" + username.toLowerCase() + "%");
    }

    public static Specification<User> emailContains(String email) {
        return (root, query, cb) ->
                cb.like(cb.lower(root.get("email")),
                        "%" + email.toLowerCase() + "%");
    }

    public static Specification<User> cpfContains(String cpf) {
        return (root, query, cb) ->
                cb.like(root.get("cpf"),
                        "%" + cpf + "%");
    }
}
