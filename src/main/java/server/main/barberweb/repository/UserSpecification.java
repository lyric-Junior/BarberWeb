package server.main.barberweb.repository;

import org.springframework.data.jpa.domain.Specification;
import server.main.barberweb.model.entitys.User;

public class UserSpecification {

    public static Specification<User> profissionalEquals(String role) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("role"), role);
    }

    public static Specification<User> usernameContains(String username) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("username"), username);
    }

    public static Specification<User> emailContains(String email) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("email"), email);
    }

    public static Specification<User> cpfContains(String cpf){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("cpf"), cpf);
    }
}
