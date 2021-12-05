package pl.turistica.controller;

import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import pl.turistica.model.User;
import pl.turistica.repository.RoleRepository;
import pl.turistica.repository.UserRepository;
import pl.turistica.service.EmailService;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:4200", "https://turistica.herokuapp.com"})
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> registerNewUser(@RequestBody User user){
        user.setRole(roleRepository.getById(1));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if(userRepository.countAllByEmail(user.getEmail())==0){
            userRepository.save(user);
            new Thread(() -> {
                try {
                    emailService.sendMessage(
                            user.getEmail(),
                            "Potwierdzenie rejestracji",
                            null,
                            "register-email-template.ftl");
                } catch (MessagingException | IOException | TemplateException e) {
                    e.printStackTrace();
                }
            }).start();
            return new ResponseEntity<>(null, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }
    }


}
