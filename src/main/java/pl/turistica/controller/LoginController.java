package pl.turistica.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.turistica.model.User;
import pl.turistica.service.LoginService;

import java.util.HashMap;

@RestController
@CrossOrigin(origins = {"http://localhost:4200", "https://turistica.herokuapp.com"})
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public HashMap<String, String> login(@RequestBody User user) {
        return loginService.login(user);
    }

}
