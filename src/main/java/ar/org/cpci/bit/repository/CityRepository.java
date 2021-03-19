package ar.org.cpci.bit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.org.cpci.bit.model.User;

public interface CityRepository extends JpaRepository<User, Long> {

}
