package pl.turistica.controller;

import freemarker.template.TemplateException;
import net.bytebuddy.utility.RandomString;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import pl.turistica.dto.ChangePasswordDTO;
import pl.turistica.dto.UserForAdminDTO;
import pl.turistica.model.User;
import pl.turistica.repository.RoleRepository;
import pl.turistica.repository.UserRepository;
import pl.turistica.service.EmailService;
import pl.turistica.service.TokenService;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.*;

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

    @Autowired
    private TokenService tokenService;

    @PutMapping("/change-password")
    public ResponseEntity<?> changeUserPassword(@RequestHeader("Authorization") String authorizationHeader,
                                                @RequestBody ChangePasswordDTO changePasswordDTO) {

        String email = tokenService.getEmailFromAuthorizationHeader(authorizationHeader);
        User user = userRepository.findByEmail(email);

        if(user!=null && passwordEncoder.matches(changePasswordDTO.getOldPassword(), user.getPassword())){
            user.setPassword(passwordEncoder.encode(changePasswordDTO.getNewPassword()));
            userRepository.save(user);
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @GetMapping("/user-list")
    public List<UserForAdminDTO> getAllUsers(){
        List<User> users = userRepository.findAll();
        List<UserForAdminDTO> usersToSend = new ArrayList<>();
        for(User u: users)
            if(u.getRole().getId()==2 || u.getRole().getId() == 3)
                usersToSend.add(new UserForAdminDTO(u));
        return usersToSend;
    }

    @PostMapping("/add-user")
    public ResponseEntity<?> addNewGuideOrAdmin(@RequestBody UserForAdminDTO user) {
        if(userRepository.countAllByEmail(user.getEmail())>0)
            return new ResponseEntity<>(HttpStatus.CONFLICT);

        String generatedPassword = RandomString.make(12);
        String encodedPassword = DigestUtils.sha256Hex(generatedPassword);
        User userToInsert = new User(user.getEmail(), passwordEncoder.encode(encodedPassword),
                user.getName(), user.getLastName(), user.getPhoneNumber(), roleRepository.findByName(user.getRole()));
        userRepository.save(userToInsert);
        new Thread(() -> {
            try {
                HashMap<String, Object> details = new HashMap<>();
                if(userToInsert.getRole().getId()==2)
                    details.put("role", "przewodnika");
                else
                    details.put("role", "administratora");
                details.put("firstName", user.getName());
                details.put("lastName", user.getLastName());
                details.put("password", generatedPassword);
                emailService.sendMessage(
                        user.getEmail(),
                        "Utworzenie konta przez administratora",
                        details,
                        "adduser-email-template.ftl");
            } catch (MessagingException | IOException | TemplateException e) {
                e.printStackTrace();
            }
        }).start();
        return new ResponseEntity<>(HttpStatus.OK);
    }

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
