package pl.turistica.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.turistica.model.User;
import pl.turistica.repository.UserRepository;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class LoginController {

    @Autowired
    UserRepository userRepository;

    @PostMapping("/login")
    public boolean login(@RequestBody User user){
        User u = userRepository.findByEmail(user.getEmail());
        if(u != null)
            return u.getPassword().equals(user.getPassword());
        return false;
    }
}
