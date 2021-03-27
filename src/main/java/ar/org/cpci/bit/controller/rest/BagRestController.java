package ar.org.cpci.bit.controller.rest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Optional;

import ar.org.cpci.bit.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.org.cpci.bit.repository.JobRepository;
import ar.org.cpci.bit.repository.UserRepository;

@RestController
public class BagRestController {

    public static final String CREATED_JOBS_URL = "/api/bag/user/{uid}/job/{jid}/created";
    public static final String INTERESTED_JOBS_URL = "/api/bag/user/{uid}/job/{jid}/interested";
    public static final String APPLY_JOBS_URL = "/api/bag/user/{uid}/job/{jid}/apply";
    public static final String CONTACT_USER_URL = "/api/bag/user/{uid1}/user/{uid2}/contact";

    public static final HttpStatus STATE_OK = HttpStatus.OK;
    public static final HttpStatus STATE_FAIL = HttpStatus.NOT_FOUND;

    @Autowired
    private UserRepository repositoryUser;

    @Autowired
    private JobRepository repositoryJob;

    @Autowired
    private CityRepository repositoryCity;


    /* === SCHMagic === functions using java generics === */

    /**
     * This function searches for an object in a repository by its identifier.
     *
     * @param  repo Generic type repository with long type identifier.
     * @param  id   Identifier to search for.
     *
     * @return      Returns the searched object of the generic type (T) if found,
     *              otherwise returns null.
     */
    private static <T> T getByIdFromRepo(CrudRepository<T, Long> repo, Long id) {
        Optional<T> optional = repo.findById(id);
        return optional.isPresent() ? optional.get() : null;
    }

    /**
     * This function searches in the "repo1" with the "id1" for an object (obj1) to which
     * it will call its method defined in "methodName" passing as argument another object
     * (obj2) searched in the repo2 with the id2, encapsulating the response in a
     * ResponseEntity, which also the HTTP status depending on the result of the process
     * described above. Both objects are persisted if the operation is successful.
     *
     * @param  repo1      Generic type repository with long type identifier.
     * @param  id1        Identifier to search for in repo1.
     * @param  repo2      Generic type repository with long type identifier.
     * @param  id2        Identifier to search for in repo2.
     * @param  methodName Name of the method to be executed, designed for setters
     *                    and getters that return booleans.
     *
     * @return            Returns an ResponseEntity object with a status and value,
     *                    described above.
     *
     * @see               getByIdFromRepo
     */
    private static <T1, T2> ResponseEntity<Object> processMethod(CrudRepository<T1, Long> repo1, Long id1,
                                                                 CrudRepository<T2, Long> repo2, Long id2,
                                                                 String methodName) {
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        boolean success = false;

        T1 obj1 = getByIdFromRepo(repo1, id1);
        T2 obj2 = getByIdFromRepo(repo2, id2);

        if (obj1 != null && obj2 != null) {
            try {
                Method method = obj1.getClass().getDeclaredMethod(methodName, obj2.getClass());
                Object result = method.invoke(obj1, obj2);
                success = result == null || (boolean) result;
                if (success) {
                    repo1.save(obj1);
                    repo2.save(obj2);
                    status = STATE_OK;
                }
            } catch (NoSuchMethodException | SecurityException | IllegalAccessException |
                     IllegalArgumentException | InvocationTargetException e) {
                // Operation not possible
                e.printStackTrace();
            }
        }
        HashMap<String, Boolean> map = new HashMap<>();
        map.put("success", success);
        return ResponseEntity.status(status).body(map);
    }

    /**
     * This function is a gateway to the processMethod function, for more details see this
     * function.
     *
     * @param  uid        User identifier.
     * @param  jid        Job identifier.
     * @param  methodName name of a method to call.
     *
     * @return            Returns an ResponseEntity object
     *
     * @see               processMethod
     */
    private ResponseEntity<Object> getResponseUserJob(Long uid, Long jid, String methodName) {
        return processMethod(repositoryUser, uid, repositoryJob, jid, methodName);
    }

    /**
     * This function is a gateway to the processMethod function, for more details see this
     * function.
     *
     * @param  uid1       User identifier.
     * @param  uid2       User identifier.
     * @param  methodName name of a method to call.
     *
     * @return            Returns an ResponseEntity object.
     *
     * @see               processMethod
     */
    private ResponseEntity<Object> getResponseUserUser(Long uid1, Long uid2, String methodName) {
        return processMethod(repositoryUser, uid1, repositoryUser, uid2, methodName);
    }

    /**
     * Function that searches for an object by its identifier in a repository,
     * both passed by parameter, and returns a ResponseEntity with the value if it was
     * found, or a "success=false" otherwise.
     *
     * @param  repo Generic type repository with long type identifier.
     * @param  id   Identifier to search for.
     *
     * @return      Returns an ResponseEntity object.
     *
     * @see         getByIdFromRepo
     */
    private static <T> ResponseEntity<Object> getResponseById(CrudRepository<T, Long> repo, Long id) {
        T obj = getByIdFromRepo(repo, id);
        if (obj != null) {
            return ResponseEntity.status(STATE_OK).body(obj);
        }
        HashMap<String, Boolean> map = new HashMap<>();
        map.put("success", false);
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(map);
    }

    /* ============ End of generic functions ============ */

    /* --- User.createdJob<job> --- */

    @GetMapping(CREATED_JOBS_URL)
    public ResponseEntity<Object> getCreatedJob(@PathVariable("uid") Long uid,
                                                @PathVariable("jid") Long jid) {
        return getResponseUserJob(uid, jid, "containsCreatedJob");
    }

    @PostMapping(CREATED_JOBS_URL)
    public ResponseEntity<Object> newCreatedJob(@PathVariable("uid") Long uid,
                                                @PathVariable("jid") Long jid) {
        return getResponseUserJob(uid, jid, "addCreatedJob");
    }

    @DeleteMapping(CREATED_JOBS_URL)
    public ResponseEntity<Object> remCreatedJob(@PathVariable("uid") Long uid,
                                                @PathVariable("jid") Long jid) {
        return getResponseUserJob(uid, jid, "remCreatedJob");
    }

    /* --- User.interestingJobs<job> --- */

    @GetMapping(INTERESTED_JOBS_URL)
    public ResponseEntity<Object> getInterestedJob(@PathVariable("uid") Long uid,
                                                   @PathVariable("jid") Long jid) {
        return getResponseUserJob(uid, jid, "containsInterestingJob");
    }

    @PostMapping(INTERESTED_JOBS_URL)
    public ResponseEntity<Object> newInterestedJob(@PathVariable("uid") Long uid,
                                                   @PathVariable("jid") Long jid) {
        return getResponseUserJob(uid, jid, "addInterestingJob");
    }

    @DeleteMapping(INTERESTED_JOBS_URL)
    public ResponseEntity<Object> remInterestedJob(@PathVariable("uid") Long uid,
                                                   @PathVariable("jid") Long jid) {
        return getResponseUserJob(uid, jid, "remInterestingJob");
    }

    /* --- User.applyJobs<job> --- */

    @GetMapping(APPLY_JOBS_URL)
    public ResponseEntity<Object> getApplyJob(@PathVariable("uid") Long uid,
                                              @PathVariable("jid") Long jid) {
        return getResponseUserJob(uid, jid, "containsApplyJob");
    }

    @PostMapping(APPLY_JOBS_URL)
    public ResponseEntity<Object> newApplyJob(@PathVariable("uid") Long uid,
                                              @PathVariable("jid") Long jid) {
        return getResponseUserJob(uid, jid, "addApplyJob");
    }

    @DeleteMapping(APPLY_JOBS_URL)
    public ResponseEntity<Object> remApplyJob(@PathVariable("uid") Long uid,
                                              @PathVariable("jid") Long jid) {
        return getResponseUserJob(uid, jid, "remApplyJob");
    }

    /* --- User.contacts<user> --- */

    @GetMapping(CONTACT_USER_URL)
    public ResponseEntity<Object> getContactUser(@PathVariable("uid1") Long uid1,
                                                 @PathVariable("uid2") Long uid2) {
        return getResponseUserUser(uid1, uid2, "containsContact");
    }

    @PostMapping(CONTACT_USER_URL)
    public ResponseEntity<Object> newContactUser(@PathVariable("uid1") Long uid1,
                                                 @PathVariable("uid2") Long uid2) {
        return getResponseUserUser(uid1, uid2, "addContact");
    }

    @DeleteMapping(CONTACT_USER_URL)
    public ResponseEntity<Object> remContactUser(@PathVariable("uid1") Long uid1,
                                                 @PathVariable("uid2") Long uid2) {
        return getResponseUserUser(uid1, uid2, "remContact");
    }

    /* --- User & Job --- */

    @GetMapping("/api/bag/user/{uid}")
    public ResponseEntity<Object> getUserById(@PathVariable("uid") Long uid) {
        return getResponseById(repositoryUser, uid);
    }

    @GetMapping("/api/bag/job/{jid}")
    public ResponseEntity<Object> getJobById(@PathVariable("jid") Long jid) {
        return getResponseById(repositoryJob, jid);
    }

    @GetMapping("/api/bag/city/{cid}")
    public ResponseEntity<Object> getCityById(@PathVariable("cid") Long cid) {
        return getResponseById(repositoryCity, cid);
    }

}
