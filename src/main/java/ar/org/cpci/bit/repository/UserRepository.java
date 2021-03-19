package ar.org.cpci.bit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import ar.org.cpci.bit.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

}
