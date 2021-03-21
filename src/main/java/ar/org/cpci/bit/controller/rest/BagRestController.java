package ar.org.cpci.bit.controller.rest;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.org.cpci.bit.model.Job;
import ar.org.cpci.bit.model.User;
import ar.org.cpci.bit.repository.JobRepository;
import ar.org.cpci.bit.repository.UserRepository;

// Job
// ===
// interestedUsers Set<User>
// applyUsers Set<User>
//
// User
// ====
// createdJobs Set<Job>
// interestingJobs Set<Job>
// applyJobs Set<Job>
// contacts Set<User>

@RestController
public class BagRestController {

    @Autowired
    private UserRepository repositoryUser;

    @Autowired
    private JobRepository repositoryJob;

    private static <T> T getFromRepo(CrudRepository<T, Long> repo, Long id) {
        Optional<T> optional = repo.findById(id);
        return optional.isPresent() ? optional.get() : null;
    }

    private ResponseEntity<Object> getResponseEntity(Long uid, Long jid, String methodName) {
        User user = getFromRepo(repositoryUser, uid);
        Job job = getFromRepo(repositoryJob, jid);

        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        boolean success = false;

        if (user != null && job != null) {
            try {
                Method method = user.getClass().getDeclaredMethod(methodName, Job.class);
                Object ret = method.invoke(user, job);
                success = ret == null || (boolean) ret;
                if (success) {
                    status = HttpStatus.OK;
                    success = true;
                    repositoryUser.save(user);
                    repositoryJob.save(job);
                }
            } catch (Exception e) {
                // not possible
                e.printStackTrace();
            }
        }
        HashMap<String, Boolean> map = new HashMap<>();
        map.put("success", success);
        return ResponseEntity.status(status).body(map);
    }

    /* --- interested --- */

    @GetMapping(path = "/api/bag/user/{uid}/job/{jid}/interested",
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getInterested(@PathVariable("uid") Long uid,
                                                @PathVariable("jid") Long jid) {
        return getResponseEntity(uid, jid, "containsInterestingJob");
    }

    @PostMapping(path = "/api/bag/user/{uid}/job/{jid}/interested",
                 produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> newInterested(@PathVariable("uid") Long uid,
                                                @PathVariable("jid") Long jid) {
        return getResponseEntity(uid, jid, "addInterestingJob");
    }

    @DeleteMapping(path = "/api/bag/user/{uid}/job/{jid}/interested",
                   produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> remInterested(@PathVariable("uid") Long uid,
                                                @PathVariable("jid") Long jid) {
        return getResponseEntity(uid, jid, "remInterestingJob");
    }

}
