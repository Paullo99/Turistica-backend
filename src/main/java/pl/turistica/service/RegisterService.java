package pl.turistica.service;

import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.turistica.model.User;
import pl.turistica.repository.RoleRepository;
import pl.turistica.repository.UserRepository;

import javax.mail.MessagingException;
import java.io.IOException;

@Service
public class RegisterService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    public ResponseEntity<?> registerNewUser(User user) {
        user.setRole(roleRepository.findByName("ROLE_USER"));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (!userRepository.existsUserByEmail(user.getEmail())) {
            userRepository.save(user);
            try {
                emailService.sendRegistrationMessage(user.getEmail());
            } catch (MessagingException | IOException | TemplateException e) {
                e.printStackTrace();
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<>(null, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }
    }
}
