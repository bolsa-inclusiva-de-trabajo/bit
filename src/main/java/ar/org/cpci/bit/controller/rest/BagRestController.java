package ar.org.cpci.bit.controller.rest;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Optional;

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

    @Autowired
    private UserRepository repositoryUser;

    @Autowired
    private JobRepository repositoryJob;

    /* --- SCHMagic :) --- */

    private static <T> T getByIdFromRepo(CrudRepository<T, Long> repo, Long id) {
        Optional<T> optional = repo.findById(id);
        return optional.isPresent() ? optional.get() : null;
    }

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
                    status = HttpStatus.OK;
                }
            } catch (Exception e) {
                // Operation not possible
                e.printStackTrace();
            }
        }
        HashMap<String, Boolean> map = new HashMap<>();
        map.put("success", success);
        return ResponseEntity.status(status).body(map);
    }

    private ResponseEntity<Object> getResponseUserJob(Long uid, Long jid, String methodName) {
        return processMethod(repositoryUser, uid, repositoryJob, jid, methodName);
    }

    private ResponseEntity<Object> getResponseUserUser(Long uid1, Long uid2, String methodName) {
        return processMethod(repositoryUser, uid1, repositoryUser, uid2, methodName);
    }

    private static <T> ResponseEntity<Object> getResponseById(CrudRepository<T, Long> repo, Long id) {
        T obj = getByIdFromRepo(repo, id);
        if (obj != null) {
            return ResponseEntity.status(HttpStatus.OK).body(obj);
        }
        HashMap<String, Boolean> map = new HashMap<>();
        map.put("success", false);
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(map);
    }

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

}
