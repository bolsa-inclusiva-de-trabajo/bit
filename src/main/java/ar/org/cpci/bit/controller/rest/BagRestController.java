package ar.org.cpci.bit.controller.rest;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Optional;

import ar.org.cpci.bit.model.Job;
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

    @Autowired
    private UserRepository repositoryUser;

    @Autowired
    private JobRepository repositoryJob;

    /* --- SCHMagic :) --- */

    private static <T> T getByeIdFromRepo(CrudRepository<T, Long> repo, Long id) {
        Optional<T> optional = repo.findById(id);
        return optional.isPresent() ? optional.get() : null;
    }

    private static <T1, T2> ResponseEntity<Object> processMethod(CrudRepository<T1, Long> repo1,
                                                                 Long id1,
                                                                 CrudRepository<T2, Long> repo2,
                                                                 Long id2,
                                                                 String methodName) {

        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        boolean success = false;

        T1 obj1 = getByeIdFromRepo(repo1, id1);
        T2 obj2 = getByeIdFromRepo(repo2, id2);

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
                // operation not possible
                e.printStackTrace();
            }
        }
        HashMap<String, Boolean> map = new HashMap<>();
        map.put("success", success);
        return ResponseEntity.status(status).body(map);
    }

    private ResponseEntity<Object> getResponseEntityUserJob(Long uid, Long jid, String methodName) {
        return processMethod(repositoryUser, uid, repositoryJob, jid, methodName);
    }

    private ResponseEntity<Object> getResponseEntityUserUser(Long id1, Long id2, String methodName) {
        return processMethod(repositoryUser, id1, repositoryUser, id2, methodName);
    }

    /* --- User.interestingJobs<job> --- */

    @GetMapping(path = "/api/bag/user/{uid}/job/{jid}/created")
    public ResponseEntity<Object> getCreatedJob(@PathVariable("uid") Long uid,
                                                @PathVariable("jid") Long jid) {
        return getResponseEntityUserJob(uid, jid, "containsCreatedJob");
    }

    @PostMapping(path = "/api/bag/user/{uid}/job/{jid}/created")
    public ResponseEntity<Object> newCreatedJob(@PathVariable("uid") Long uid,
                                                @PathVariable("jid") Long jid) {
        return getResponseEntityUserJob(uid, jid, "addCreatedJob");
    }

    @DeleteMapping(path = "/api/bag/user/{uid}/job/{jid}/created")
    public ResponseEntity<Object> remCreatedJob(@PathVariable("uid") Long uid,
                                                @PathVariable("jid") Long jid) {
        return getResponseEntityUserJob(uid, jid, "remCreatedJob");
    }

    /* --- User.interestingJobs<job> --- */

    @GetMapping(path = "/api/bag/user/{uid}/job/{jid}/interested")
    public ResponseEntity<Object> getInterestedJob(@PathVariable("uid") Long uid,
                                                   @PathVariable("jid") Long jid) {
        return getResponseEntityUserJob(uid, jid, "containsInterestingJob");
    }

    @PostMapping(path = "/api/bag/user/{uid}/job/{jid}/interested")
    public ResponseEntity<Object> newInterestedJob(@PathVariable("uid") Long uid,
                                                   @PathVariable("jid") Long jid) {
        return getResponseEntityUserJob(uid, jid, "addInterestingJob");
    }

    @DeleteMapping(path = "/api/bag/user/{uid}/job/{jid}/interested")
    public ResponseEntity<Object> remInterestedJob(@PathVariable("uid") Long uid,
                                                   @PathVariable("jid") Long jid) {
        return getResponseEntityUserJob(uid, jid, "remInterestingJob");
    }

    /* --- User.applyJobs<job> --- */

    @GetMapping(path = "/api/bag/user/{uid}/job/{jid}/apply")
    public ResponseEntity<Object> getApplyJob(@PathVariable("uid") Long uid,
                                              @PathVariable("jid") Long jid) {
        return getResponseEntityUserJob(uid, jid, "containsApplyJob");
    }

    @PostMapping(path = "/api/bag/user/{uid}/job/{jid}/apply")
    public ResponseEntity<Object> newApplyJob(@PathVariable("uid") Long uid,
                                              @PathVariable("jid") Long jid) {
        return getResponseEntityUserJob(uid, jid, "addApplyJob");
    }

    @DeleteMapping(path = "/api/bag/user/{uid}/job/{jid}/apply")
    public ResponseEntity<Object> remApplyJob(@PathVariable("uid") Long uid,
                                              @PathVariable("jid") Long jid) {
        return getResponseEntityUserJob(uid, jid, "remApplyJob");
    }

    /* --- User.contacts<user> --- */

    @GetMapping(path = "/api/bag/user/{uid}/job/{jid}/contact")
    public ResponseEntity<Object> getContact(@PathVariable("uid") Long uid1,
                                             @PathVariable("uid") Long uid2) {
        return getResponseEntityUserUser(uid1, uid2, "containsContact");
    }

    @PostMapping(path = "/api/bag/user/{uid}/job/{jid}/contact")
    public ResponseEntity<Object> newContact(@PathVariable("uid") Long uid1,
                                             @PathVariable("uid") Long uid2) {
        return getResponseEntityUserUser(uid1, uid2, "addContact");
    }

    @DeleteMapping(path = "/api/bag/user/{uid}/job/{jid}/contact")
    public ResponseEntity<Object> remContact(@PathVariable("uid") Long uid1,
                                             @PathVariable("uid") Long uid2) {
        return getResponseEntityUserUser(uid1, uid2, "remContact");
    }

    /* ------- Job ------------- */

    private static <Job> ResponseEntity<Object> processMethod(CrudRepository<Job, Long> repo1,
                                                                 Long id1) {
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        boolean success = false;

        Job obj1 = getByeIdFromRepo(repo1, id1);

        HashMap<String, Job> map = new HashMap<>();
        map.put("job", obj1);
        return ResponseEntity.status(status).body(map);
    }

    private ResponseEntity<Object> getResponseEntityJob(Long id1) {
        return processMethod(repositoryJob, id1);
    }

    @GetMapping(path = "/api/bag/job/{jid}")
    public ResponseEntity<Object> getJob(@PathVariable("jid") Long jid) {
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        Job obj1 = getByeIdFromRepo(repositoryJob, jid);
        HashMap<String, Job> map = new HashMap<>();
        map.put("job", obj1);
        return ResponseEntity.status(status).body(map);
    }

    @GetMapping(path = "/api/bag/job_by_id/{jid}")
    public Job getJobById(@PathVariable("jid") Long jid) {
        Optional<Job> j = repositoryJob.findById(jid);
        return j.isPresent() ? j.get() : null;
    }

}
