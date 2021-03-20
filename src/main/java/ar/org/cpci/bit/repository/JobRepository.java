package ar.org.cpci.bit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.org.cpci.bit.model.Job;

public interface JobRepository extends JpaRepository<Job, Long> {

}
