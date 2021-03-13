package ar.org.cpci.bit.test;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import ar.org.cpci.bit.domain.User;

@Repository
public interface TestRepository extends PagingAndSortingRepository<User, Long> {

}
