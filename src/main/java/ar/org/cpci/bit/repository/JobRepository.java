package ar.org.cpci.bit.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ar.org.cpci.bit.model.Job;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {


    @Query(value = "SELECT j FROM Job j WHERE j.expiration > CURRENT_DATE")
    public abstract Page<Job> findAllActiveJobs(Pageable var1);


    @Query(value = "SELECT j FROM Job j WHERE (j.expiration > CURRENT_DATE) " +
            "AND ( lower(j.description) LIKE %:text% ) ")
    public abstract Page<Job> findTextFilteredJobs(Pageable var1,
                                               @Param("text") String text);

    @Query(value = "SELECT j FROM Job j WHERE (j.expiration > CURRENT_DATE) " +
            "AND ( lower(j.description) LIKE %:text% ) ")
    public abstract Iterable<Job> findTextFilteredJobs(@Param("text") String text);

    @Query(value = "SELECT j FROM Job j INNER JOIN j.owner o INNER JOIN o.city c WHERE (j.expiration > CURRENT_DATE) " +
            "AND ( c.id = :city ) ")
    public abstract Page<Job> findCityFilteredJobs(Pageable var1, @Param("city") Long city);

    @Query(value = "SELECT j FROM Job j INNER JOIN j.owner o WHERE (j.expiration > CURRENT_DATE) " +
            "AND ( o.id = :userid ) ")
    public abstract Page<Job> findMyOffersFilteredJobs(Pageable var1, @Param("userid") Long userid);

    @Query
    public Page<Job> findByFullTimeTrue(Pageable var1);

    @Query
    public Page<Job> findByPartTimeTrue(Pageable var1);

    @Query
    public Page<Job> findByHomeWorkTrue(Pageable var1);

    @Modifying
    @Transactional
    @Query(value="delete from Job j where j.id = ?1")
    public void deleteById(Long id);
}



// "AND ((j.description LIKE '%:text%') OR (j.title LIKE '%:text%')) " +

    /*


CASE e.dept WHEN 'IT' THEN 'Information Technology' WHEN 'Admin' THEN 'Administration' ELSE e.dept END
CASE :city WHEN 0 THEN ( c.id = :city ) ELSE true END


        @Query(value = "SELECT j FROM Job j INNER JOIN j.owner o WHERE (j.expiration > CURRENT_DATE) " +
            "AND j.description = :text "  +
            "AND o.city = :city " +
            "AND j.fullTime = :full " +
            "AND j.partTime = :part " +
            "AND j.homeWork = :home " +
            "AND o.id = :myOffers")
    public abstract Page<Job> findFilteredJobs(Pageable var1,
                                               @Param("text") String text,
                                               @Param("city") Integer city,
                                               @Param("full") Boolean full,
                                               @Param("part") Boolean part,
                                               @Param("home") Boolean home,
                                               @Param("myOffers") Boolean myOffers );



 @Query(value = "SELECT j FROM Job j INNER JOIN j.owner o INNER JOIN o.city c WHERE (j.expiration > CURRENT_DATE) " +
            "AND ( j.description LIKE %:text% ) " +
            "AND ( c.id = :city ) ")


     */
