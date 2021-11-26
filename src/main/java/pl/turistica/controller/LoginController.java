package pl.turistica.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.turistica.model.User;
import pl.turistica.repository.UserRepository;

import java.util.HashMap;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class LoginController {

    @Autowired
    UserRepository userRepository;

    @PostMapping("/login")
    public HashMap<String, String> login(@RequestBody User user){
        HashMap<String, String> responseJson = new HashMap<>();
        User u = userRepository.findByEmail(user.getEmail());
        if(u != null){
            responseJson.put("isValid", String.valueOf(u.getPassword().equals(user.getPassword())));
            responseJson.put("role", u.getRole().getName());
            return responseJson;
        }
        responseJson.put("isValid", "false");
        return responseJson;
    }
}
