package ar.org.cpci.bit.repository;

import java.util.List;
import java.util.Optional;

import ar.org.cpci.bit.model.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import ar.org.cpci.bit.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    public abstract List<User> findByApplyForJobTrue(Pageable var1);

    public abstract List<User> findByApplyForJobTrueAndDisabledFalse(Pageable var1);

    @Query(value = "SELECT u FROM User u WHERE ( lower(u.skills) LIKE %:text% ) OR ( lower(u.education) LIKE %:text% )")
    public abstract Page<User> findTextFilteredUsers(Pageable var1,@Param("text") String text);

    @Query(value = "SELECT u FROM User u WHERE ( lower(u.skills) LIKE %:text% ) OR ( lower(u.education) LIKE %:text% )")
    public abstract Iterable<User> findTextFilteredUsers(@Param("text") String text);

    @Query(value = "SELECT u FROM User u INNER JOIN u.city c WHERE  c.id = :city ")
    public abstract Page<User> findCityFilteredUsers(Pageable var1, @Param("city") Long city);

    @Query
    public Page<User> findByApplyForJobTrueAndDisabledFalseAndFullTimeTrue(Pageable var1);

    @Query
    public Page<User> findByApplyForJobTrueAndDisabledFalseAndPartTimeTrue(Pageable var1);

    @Query
    public Page<User> findByApplyForJobTrueAndDisabledFalseAndHomeWorkTrue(Pageable var1);
}
