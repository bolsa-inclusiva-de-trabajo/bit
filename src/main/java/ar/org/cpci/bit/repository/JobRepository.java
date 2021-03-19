package ar.org.cpci.bit.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import ar.org.cpci.bit.model.Job;

public interface JobRepository extends PagingAndSortingRepository<Job, Long> {

}
