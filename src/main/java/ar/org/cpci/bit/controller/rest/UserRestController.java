package ar.org.cpci.bit.controller.rest;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ar.org.cpci.bit.model.User;
import ar.org.cpci.bit.repository.UserRepository;

@RestController
public class UserRestController {
	@Autowired
    private UserRepository repositoryUser;
	 @GetMapping("/api/existsByUsername/{name}")
	    public boolean existsByUsername(@PathVariable String name) {
	    	
	        boolean exist = repositoryUser.existsByUsername(name);
	        return exist;
	 }
	 @GetMapping("/api/existsByEMail/{email}")
	    public boolean existsByEMail(@PathVariable String email) {
	    	
	        boolean exist = repositoryUser.existsByEmail(email);
	        return exist;
	 }
	 @GetMapping("/api/user/{uid}")
	    public ResponseEntity<Object> getUser(@PathVariable Long uid) {
	    	
		Optional<User> user = repositoryUser.findById(uid);
        if (user.isPresent()) {
            return ResponseEntity.ok().body(user.get());
        }
        return new ResponseEntity<>(uid, HttpStatus.NOT_FOUND);
	 }
	 @PostMapping("/api/user")
	 public User crear(@RequestBody String user){
		 return this.repositoryUser.findById((long) 1).get();
	    }
}
