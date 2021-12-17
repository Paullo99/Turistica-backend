package pl.turistica.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.turistica.model.User;
import pl.turistica.service.RegisterService;

@RestController
@CrossOrigin(origins = {"http://localhost:4200", "https://turistica.herokuapp.com"})
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    @PostMapping("/register")
    public ResponseEntity<?> registerNewUser(@RequestBody User user) {
            return registerService.registerNewUser(user);
    }

}
