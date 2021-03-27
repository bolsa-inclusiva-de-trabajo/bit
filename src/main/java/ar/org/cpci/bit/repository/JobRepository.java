package ar.org.cpci.bit.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import ar.org.cpci.bit.model.Job;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface JobRepository extends JpaRepository<Job, Long> {


    @Query(value = "SELECT j FROM Job j WHERE j.expiration > CURRENT_DATE")
    public abstract Page<Job> findAllActiveJobs(Pageable var1);


}
