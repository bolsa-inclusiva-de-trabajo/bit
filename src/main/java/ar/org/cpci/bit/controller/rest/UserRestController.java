package ar.org.cpci.bit.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
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
	 @PostMapping("/api/user")
	 public User crear(@RequestBody String user){
		 System.out.println(user);
		 return this.repositoryUser.findById((long) 1).get();
//	        return  this.repository.save(encuesta);
	    }
}
