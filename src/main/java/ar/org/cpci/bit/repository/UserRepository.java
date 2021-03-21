package ar.org.cpci.bit.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.org.cpci.bit.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
    
    boolean existsByUsername(String username);

}
