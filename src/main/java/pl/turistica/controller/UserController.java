package pl.turistica.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.turistica.model.User;
import pl.turistica.repository.RoleRepository;
import pl.turistica.repository.UserRepository;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/test")
    public int getShit(){
        return userRepository.countAllByEmail("test@test.pl");
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerNewUser(@RequestBody User user){
        user.setRole(roleRepository.getById(1));
        if(userRepository.countAllByEmail(user.getEmail())==0){
            System.out.println(user.toString());
            userRepository.save(user);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }
    }
}
