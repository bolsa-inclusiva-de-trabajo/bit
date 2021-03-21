package ar.org.cpci.bit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.org.cpci.bit.model.location.City;

public interface CityRepository extends JpaRepository<City, Long> {

}
